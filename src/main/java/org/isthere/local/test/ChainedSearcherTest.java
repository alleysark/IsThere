package org.isthere.local.test;

import org.isthere.IndexCreator;
import net.semanticmetadata.lire.DescriptorWeight;
import net.semanticmetadata.lire.DocumentBuilder;
import net.semanticmetadata.lire.ImageSearcher;
import net.semanticmetadata.lire.ImageSearcherFactory;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for ChainedSearcher.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 23
 *        Time: 오전 3:24
 */
public class ChainedSearcherTest {
    private static String trainFilePath = "D:\\GIT\\IsThere\\src\\main\\webapp\\img";
    private static String indexPath = "D:\\GIT\\IsThere\\src\\main\\webapp\\index";
    private static String testFilePath = "D:\\GIT\\IsThere\\src\\main\\webapp\\img\\"+"4120090028374.jpg";

    public static void main(String[] args) throws IOException {
        IndexCreator indexCreaterCreator = new IndexCreator(trainFilePath, indexPath);
        indexCreaterCreator.create();
        IndexReader reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
        ArrayList<DescriptorWeight> descriptorWeights = new ArrayList<DescriptorWeight>();
        descriptorWeights.add(new DescriptorWeight("EdgeHistogram", 6f));
        descriptorWeights.add(new DescriptorWeight("ColorLayout", 2f));
        descriptorWeights.add(new DescriptorWeight("AutoColorCorrelogram",2f));
//
        ImageSearcher searcher = ImageSearcherFactory.createChainedSearcher(Integer.MAX_VALUE, descriptorWeights);


        FileInputStream imageStream = new FileInputStream(testFilePath);
        BufferedImage bimg = ImageIO.read(imageStream);
        net.semanticmetadata.lire.ImageSearchHits hits = null;
        hits = searcher.search(bimg, reader);





        System.out.println();
        List<Fieldable> fields = hits.doc(0).getFields();
        for (Fieldable a : fields) {
            System.out.println(a.name() + " ");
        }
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.score(i) + ": " + hits.doc(i).getFieldable(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
        }
    }
}
