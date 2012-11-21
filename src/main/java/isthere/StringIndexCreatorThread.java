package isthere;

import java.io.*;
import java.util.ArrayList;

/**
 * 멀티쓰레드로 인덱스를 만들기 위한 스레드 클래스.
 *
 * User: Hyunje
 * Date: 12. 11. 16
 * Time: 오전 4:30
 */
public class StringIndexCreatorThread extends Thread {
    String input;
    String output;
    String mD;
    String sD;


    File file;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;


    public StringIndexCreatorThread(String input, String output, String mD, String sD) {

        this.input = input;
        this.output = output;
        this.mD = mD;
        this.sD = sD;


        try {
            file = new File(output);
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            ArrayList<String> images = getAllImages(new File(input), true);
            if (images == null) {
                System.out.println("Could not find any files in " + input);
                return;
            }

            IsThereDocumentBuilder builder = new IsThereDocumentBuilder(mD, sD);
            int count = 0;
            IsThereDocument doc;
            ParallelStringIndexer indexer = new ParallelStringIndexer(images, builder);


            Thread t = new Thread(indexer);
            t.start();



            while ((doc = indexer.getNext()) != null) {
                try{
                    //Write to file
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(doc.getValues());
                    bufferedWriter.write(stringBuilder.toString());
                    bufferedWriter.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
