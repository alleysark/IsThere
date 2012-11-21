package isthere;

import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.DocumentBuilderFactory;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for creating index of images in a directory.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 14
 *        Time: 오전 6:01
 */
public class IndexCreator {
    private ArrayList<String> images;
    private String testFilePath;
    private String indexPath;

    /**
     * Constructor for test.isthere.CreateIndex class.
     *
     * @param testimgPath path of test images
     * @param idxPath     path of index
     */
    public IndexCreator(String testimgPath, String idxPath) {
        testFilePath = testimgPath;
        indexPath = idxPath;
    }

    /**
     * Create index
     *
     * @throws java.io.IOException
     */
    public void create() throws IOException {
//        DocumentBuilder builder = DocumentBuilderFactory.getCEDDDocumentBuilder();

        /*
        net.semanticmetadata.DocumentBuilderFactory.getAutoColorCorrelogramDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getCEDDDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getColorHistogramDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getColorLayoutBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getEdgeHistogramBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getFCTHDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getGaborDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getJCDDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getJpegCoefficientHistogramDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getScalableColorBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getTamuraDocumentBuilder();
        net.semanticmetadata.DocumentBuilderFactory.getSURFDocumentBuilder();
        */
        DocumentBuilder allBuilder = DocumentBuilderFactory.getExtensiveDocumentBuilderWithSIFT();
        IndexWriter indexWriter = LuceneUtils.createIndexWriter(indexPath, true);
        images = getAllImages(new File(testFilePath), true);
        System.out.println();
        int size = images.size();
        int count = 0;
        for (String identifier : images) {
            Document document = allBuilder.createDocument(new FileInputStream(identifier), identifier);
            indexWriter.addDocument(document);
            count++;
            if (count % 10 == 0)
                System.out.println(count + "/" + size);
        }
        indexWriter.close();
    }

    /**
     * Get all images from a directory
     *
     * @param directory directory path
     */
    private ArrayList<String> getAllImages(File directory, boolean descendIntoSubDirectories) throws IOException {
        ArrayList<String> resultList = new ArrayList<String>(256);
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
