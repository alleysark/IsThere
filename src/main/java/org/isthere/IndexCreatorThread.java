package org.isthere;

import org.isthere.local.test.IsThereFrame;
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.indexing.ParallelIndexer;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class for indexing with thread
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 31
 *        Time: 오후 9:45
 */
public class IndexCreatorThread extends Thread {
    IsThereFrame parent;

    public IndexCreatorThread(IsThereFrame parent) {
        this.parent = parent;
    }

    public void run() {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.setMaximumFractionDigits(0);
        df.setMinimumFractionDigits(0);
        try {
            parent.progressBar.setValue(0);
            ArrayList<String> images = getAllImages(new File(parent.trainPath), true);

            if (images == null) {
                JOptionPane.showMessageDialog(parent, "Could not find any files");
                return;
            }
            IndexWriter iw;
            iw = LuceneUtils.createIndexWriter(parent.indexPath, true);
            DocumentBuilder allBuilder = DocumentBuilderFactory.getExtensiveDocumentBuilderWithSIFT();
            int count = 0;
            long time = System.currentTimeMillis();
            Document doc;
            ParallelIndexer indexer = new ParallelIndexer(images, allBuilder);
            new Thread(indexer).start();
            while ((doc = indexer.getNext()) != null) {
                try {
                    iw.addDocument(doc);
                } catch (Exception e) {
                    System.err.println("Could not add document");
                    e.printStackTrace();
                }
                count++;
                float percentage = (float) count / (float) images.size();
                parent.progressBar.setValue((int) Math.floor(100f * percentage));
                float msleft = (float) (System.currentTimeMillis() - time) / percentage;
                float secLeft = msleft * (1 - percentage) / 1000f;
                String toPaint = "~ " + df.format(secLeft) + " sec. left";
                if (secLeft > 90) toPaint = "~ " + Math.ceil(secLeft / 60) + " min. left";
                parent.progressBar.setString(toPaint);
            }
            long timeTaken = (System.currentTimeMillis() - time);
            float sec = ((float) timeTaken) / 1000f;
            System.out.println("Finished indexing ...");
            parent.progressBar.setString(Math.round(sec) + " sec. for " + count + " files");
            parent.progressBar.setValue(100);
            iw.optimize();
            iw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
