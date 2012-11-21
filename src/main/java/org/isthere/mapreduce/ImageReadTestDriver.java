package org.isthere.mapreduce;

import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * HDFS의 image 를 URL로 불러들이는 작업이 가능한지 테스트 하는 Driver.
 * User: Hyunje
 * Date: 12. 11. 17
 * Time: 오전 6:20
 * To change this template use File | Settings | File Templates.
 */
public class ImageReadTestDriver extends Configured implements Tool {
    Job mainJob;

    String listFile;
    String imagePath;
    String outputPath;

    private final String[][] requiredOptions =
        {
            {"l", "Pleas input 'input file'"},
            {"p", "Pleas input 'path of images'"},
            {"o", "Please input 'output path'"},
        };

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("l", "list", true, "input list file path");
        options.addOption("p", "path", true, "input path of images");
        options.addOption("o", "output", true, "output path (required)");
        return options;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("in Main, args : " + args.length);
        int res = ToolRunner.run(new ImageReadTestDriver(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        mainJob = new Job();

        parseArguments(args);


        mainJob.setMapperClass(ImageReadTestMapper.class);
        mainJob.setMapOutputKeyClass(NullWritable.class);
        mainJob.setMapOutputValueClass(Text.class);
        mainJob.setNumReduceTasks(0);
        mainJob.setJarByClass(ImageReadTestDriver.class);
        return mainJob.waitForCompletion(true) ? 0 : 1;
    }

    private void parseArguments(String[] args) throws ParseException, IOException {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        System.out.println("length in parseArguments : " + args.length);
        if (args.length == 0) {
            formatter.printHelp("hadoop jar <JAR> " + getClass().getName(), options, true);
            return;
        }


        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        for (String[] requiredOption : requiredOptions) {
            if (!cmd.hasOption(requiredOption[0])) {
                System.out.println("in if");
                formatter.printHelp("hadoop jar <JAR> " + getClass().getName(), options, true);
                return;
            }
        }

        if (cmd.hasOption("l")) {
            listFile = cmd.getOptionValue("l");
            FileInputFormat.addInputPath(mainJob, new Path(listFile));
        }
        if (cmd.hasOption("o")) {
            outputPath = cmd.getOptionValue("o");
            System.out.println("outputDirectory : " + outputPath);
            FileOutputFormat.setOutputPath(mainJob, new Path(outputPath));
        }
        if (cmd.hasOption("p")) {
            imagePath = cmd.getOptionValue("p");
            mainJob.getConfiguration().set("imagePath".toUpperCase(), imagePath);

        }
    }
}
