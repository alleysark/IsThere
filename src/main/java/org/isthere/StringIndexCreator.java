package org.isthere;

import net.semanticmetadata.lire.imageanalysis.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.EdgeHistogram;
import net.semanticmetadata.lire.imageanalysis.FCTH;
import net.semanticmetadata.lire.imageanalysis.LireFeature;
import org.apache.commons.cli.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * 루씬의 인덱스 파일이 아닌 일반적 문자열 형태의 파일을 생성하는 인덱스 생성 클래스
 * User: Hyunje
 * Date: 12. 11. 12
 * Time: 오후 11:00
 *
 * @deprecated Use IsThereDocumentBuilder, StringIndexCreatorThread, ParallelStringIndexer
 */
public class StringIndexCreator {
    ArrayList<String> images;
    ArrayList<LireFeature> selectedFeatures;
    String inputDirectory;
    String outputFile;
    String firstDelim;
    String secondDelim;

    public StringIndexCreator(String[] args, Options options, String[][] requiredOptions) {
        try {
            parseArguments(args, options, requiredOptions);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        images = new ArrayList<String>();
        selectedFeatures = new ArrayList<LireFeature>();

        inputLireFeatures();
    }

    private int parseArguments(String[] args, Options options, String[][] requiredOptions) throws ParseException {
        HelpFormatter formatter = new HelpFormatter();
        if (args.length == 0) {
            formatter.printHelp("java jar <JAR> " + "ServersideIndexCreator", options, true);
            return -1;
        }


        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        for (String[] requiredOption : requiredOptions) {
            if (!cmd.hasOption(requiredOption[0])) {
                formatter.printHelp("java jar <JAR> " + "ServersideIndexCreator", options, true);
                return -1;
            }
        }

        if (cmd.hasOption("i")) {
            inputDirectory = cmd.getOptionValue("i");
        }
        if (cmd.hasOption("o")) {
            outputFile = cmd.getOptionValue("o");
        }
        if (cmd.hasOption("md")) {
            firstDelim = cmd.getOptionValue("md");
        }
        if (cmd.hasOption("sd")) {
            secondDelim = cmd.getOptionValue("sd");
        }

        return 0;

    }

    private void inputLireFeatures() {
        selectedFeatures.add(new AutoColorCorrelogram());
        selectedFeatures.add(new EdgeHistogram());
        selectedFeatures.add(new FCTH());
    }

    public void create() throws IOException {
        File indexFile = new File(outputFile);
        FileWriter fileWriter = new FileWriter(indexFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        images = getAllImages(new File(inputDirectory), true);
        System.out.println();
        int size = images.size();
        int count = 0;

        for (String identifier : images) {
            FileInputStream stream = new FileInputStream(identifier);
            BufferedImage image = ImageIO.read(stream);
            StringBuilder builder = new StringBuilder();
            builder.append(identifier + firstDelim);
            for (LireFeature feature : selectedFeatures) {
                feature.extract(image);
                builder.append(feature.getStringRepresentation());
//                builder.append(feature.getByteArrayRepresentation());
                builder.append(secondDelim);
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("\n");
//            Document document = allBuilder.createDocument(new FileInputStream(identifier),identifier);
            bufferedWriter.write(builder.toString());


            count++;
            System.out.println(count + "/" + size);
        }

    }


    private ArrayList<String> getAllImages(File directory, boolean descendIntoSubDirectories) throws IOException {
        ArrayList<String> resultList = new ArrayList<String>();
        File[] f = directory.listFiles();
        for (File file : f) {
            if (file != null && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png")) && !file.getName().startsWith("tn_")) {
                resultList.add(file.getCanonicalPath());
            }
            if (descendIntoSubDirectories && file.isDirectory()) {
                ArrayList<String> tmp = getAllImages(file, true);
                if (tmp != null) {
                    resultList.addAll(tmp);
                }
            }
        }
        if (resultList.size() > 0)
            return resultList;
        else
            return null;
    }
}
