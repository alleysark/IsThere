package org.isthere.searcher;

import org.isthere.IndexCreator;
import net.semanticmetadata.lire.DescriptorWeight;
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimillerImageSearcher {
    public List<Map<String, String>> searchSimillerImage(InputStream uploadedInputStream) throws IOException {
        try {
            System.out.println("start");
            String trainFilePath = "D:\\GIT\\IsThere\\src\\main\\webapp\\img";
            String indexPath = "D:\\GIT\\IsThere\\src\\main\\webapp\\index";

//            IndexCreator indexCreaterCreator = new IndexCreator(trainFilePath, indexPath);
//            indexCreaterCreator.create();
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(indexPath)));


            ArrayList<DescriptorWeight> descriptorWeights = new ArrayList<DescriptorWeight>();
            descriptorWeights.add(new DescriptorWeight("EdgeHistogram", 6f));
            descriptorWeights.add(new DescriptorWeight("ColorLayout", 2f));
            descriptorWeights.add(new DescriptorWeight("AutoColorCorrelogram", 2f));
            //
            ImageSearcher searcher = ImageSearcherFactory.createChainedSearcher(100, descriptorWeights);

            //FileInputStream imageStream = new FileInputStream(testFilePath + "1.jpg");
            InputStream imageStream = uploadedInputStream;
            BufferedImage srcImage = ImageIO.read(imageStream);

            BufferedImage bimg = ImageIO.read(imageStream);
            net.semanticmetadata.lire.ImageSearchHits hits = null;
            hits = searcher.search(bimg, reader);

            List<Map<String, String>> searchResultList = new ArrayList<Map<String, String>>();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            String hitsPath = null;
            for (int i = 0; i < hits.length(); i++) {
                hitsPath = hits.doc(i).getFieldable(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();

                String appNo = hitsPath.substring(hitsPath.lastIndexOf('\\'));
                hashMap.put("url", "/img/" + appNo);
                hashMap.put("appNo", appNo);
                searchResultList.add(hashMap);
            }

            return searchResultList;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
