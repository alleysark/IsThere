package org.isthere.mapreduce;

import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Indexing Map-Reduce 작업을 수행하기 위한 Driver.
 * 입력받는 옵션은 다음과 같다.
 * - 이미지 파일의 경로(HDFS)
 * - index 파일이 생성될 경로(HDFS)
 * - 최종적으로 생성되는 index 파일의 갯수
 * - 인덱스의 main delimiter
 * - 인덱스의 semi delimiter
 * User: Hyunje
 * Date: 12. 11. 17
 * Time: 오전 12:10
 */
public class MainDriver extends Configured implements Tool {

    public static enum ISTHERE_COUNTER {
        NUM_OF_IMAGES
    }

    ;

    Job countLineJob;
    Job indexingJob;


    String imagePath;
    String listPath;
    String outputPath;
    int NUMBER_OF_INDEX;
    String mainDelim;
    String semiDelim;

    private final String[][] requiredOptions =
        {
            {"imagePath", "Pleas input 'input path'"},
            {"listPath", "Pleas input 'list path'"},
            {"outputPath", "Please input 'output path'"},
            {"n", "Please input number of index files"},
            {"mainDelim", "Please input main delimiter of index"},
            {"semiDelim", "Please input semi delimiter of index"},
        };

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("ii", "imageinput", true, "input path (required)");
        options.addOption("li", "listinput", true, "input path (required)");
        options.addOption("o", "output", true, "output path (required)");
        options.addOption("n", "numofindex", true, "number of recommended items (required)");
        options.addOption("m", "maindelim", true, "modulus K in minhash (required)");
        options.addOption("s", "semidelim", true, "num of hash group(required)");
        return options;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new MainDriver(), args);
        System.exit(res);

    }

    @Override
    public int run(String[] args) throws Exception {
        countLineJob = new Job();
        countLineJob.setMapperClass(CounterMapper.class);
        countLineJob.setMapOutputKeyClass(NullWritable.class);
        countLineJob.setMapOutputValueClass(NullWritable.class);
        countLineJob.setNumReduceTasks(0);
        countLineJob.setJarByClass(MainDriver.class);

        indexingJob = new Job();
        indexingJob.setMapperClass(IndexingMapper.class);

        parseArguments(args);
        return 0;
    }

    private void parseArguments(String[] args) throws Exception {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        if (args.length == 0) {
            formatter.printHelp("hadoop jar <JAR> " + getClass().getName(), options, true);
            return;
        }


        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        for (String[] requieqdOption : requiredOptions) {
            if (!cmd.hasOption(requieqdOption[0])) {
                formatter.printHelp("hadoop jar <JAR> " + getClass().getName(), options, true);
                return;
            }
        }

        if (cmd.hasOption("ii")) {
            imagePath = cmd.getOptionValue("ii");
            indexingJob.getConfiguration().set("imagePath".toUpperCase(), imagePath);
        }
        if (cmd.hasOption("li")) {
            listPath = cmd.getOptionValue("li");
            indexingJob.getConfiguration().set("listPath".toUpperCase(), listPath);
            FileInputFormat.addInputPaths(countLineJob, listPath);
//            FileOutputFormat.setOutputPath(countLineJob,new Path(listPath+"/count"));
        }

        if (cmd.hasOption("o")) {
            outputPath = cmd.getOptionValue("o");
            FileOutputFormat.setOutputPath(indexingJob, new Path(cmd.getOptionValue("o")));
        }
        if (cmd.hasOption("n")) {
            NUMBER_OF_INDEX = Integer.parseInt(cmd.getOptionValue("n"));
            indexingJob.getConfiguration().set("numberOfIndex".toUpperCase(), Integer.toString(NUMBER_OF_INDEX));
        }
        if (cmd.hasOption("m")) {
            mainDelim = cmd.getOptionValue("m");
            indexingJob.getConfiguration().set("mainDelim".toUpperCase(), mainDelim);

        }
        if (cmd.hasOption("s")) {
            semiDelim = cmd.getOptionValue("s");
            indexingJob.getConfiguration().set("semiDelim".toUpperCase(), semiDelim);
        }
    }
}
