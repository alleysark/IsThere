package org.isthere;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * parallel string-indexer
 * User: Hyunje
 * Date: 12. 11. 16
 * Time: 오전 6:56
 * To change this template use File | Settings | File Templates.
 */
public class ParallelStringIndexer implements Runnable {
    Vector<String> imageFiles;
    private int NUMBER_OF_SYNC_THREADS = 3;
    Hashtable<String, Boolean> indexThreads = new Hashtable<String, Boolean>(3);
    IsThereDocumentBuilder builder;
    Vector<IsThereDocument> finished = new Vector<IsThereDocument>();
    private boolean started = false;
    private final ExecutorService pool;
    private int countImagesOut = 0;
    private int totalCount=0;
    private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
//    CyclicBarrier b = new CyclicBarrier(3);
    public ParallelStringIndexer(List<String> imageFiles, IsThereDocumentBuilder b) {
        totalCount = imageFiles.size();
        this.imageFiles = new Vector<String>();
        this.imageFiles.addAll(imageFiles);
        builder = b;
        pool = Executors.newFixedThreadPool(NUMBER_OF_SYNC_THREADS);
    }

    @Override
    public void run() {


        for (int i = 0; i < NUMBER_OF_SYNC_THREADS; i++) {
            PhotoIndexer runnable = new PhotoIndexer(this);
            pool.execute(runnable);
        }
        pool.shutdown();


        //started = true;
    }


    public IsThereDocument getNext() {
        if (imageFiles.size() < 1) {
            boolean fb = true;


            for (String t : indexThreads.keySet()) {
                fb = fb && indexThreads.get(t);
            }

            if (pool.isTerminated())
                return null;

            //if (started && fb)
             //   return null;
        }

        while (finished.size() < 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return finished.remove(0);
    }

    public void addDoc(IsThereDocument doc, String photofile) {
        if (doc != null) finished.add(doc);
        Thread.yield();
    }

    private synchronized  String getNextImage() {
        if (imageFiles.size() > 0) {
            countImagesOut++;
            return imageFiles.remove(0);
        } else {
            System.out.println("countImagesOut = " + countImagesOut);
            return null;
        }

    }

    class PhotoIndexer implements Runnable {
        String photo;
        ParallelStringIndexer parent;
        private boolean hasFinished = false;

        PhotoIndexer(ParallelStringIndexer parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
           parent.indexThreads.put(Thread.currentThread().getName(), false);
            while ((photo = parent.getNextImage()) != null) {
                try {
                    BufferedImage image = readFile(photo);
                    if (image != null) {
                        IsThereDocument doc = parent.builder.createDocument(image, photo);

                        parent.addDoc(doc, photo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            parent.indexThreads.put(Thread.currentThread().getName(),true);
        }

        private BufferedImage readFile(String path) throws IOException {
            BufferedImage image = null;
            if (path.toLowerCase().endsWith(".jpg") || path.toLowerCase().endsWith(".jpeg")) {
                FileInputStream jpegFile = new FileInputStream(path);
                try {
                    image = ImageIO.read(new File(path));
                } catch (Exception e) {
                    System.err.println("Could not extract EXIF data for " + path);
                    System.err.println("\t" + e.getMessage());
                }
                jpegFile.close();
            }
            return image;
        }
    }
}
