package isthere.local.test;

import isthere.IndexCreator;
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
    private static String trainFilePath = "/Users/fharenheit/training_images";
    private static String indexPath = "./index/";
    private static String testFilePath = "/Users/fharenheit/test_images";
    private static String trainPath;

    public static void main(String[] args) throws IOException {
        IndexCreator indexCreaterCreator = new IndexCreator(trainFilePath, indexPath);
        indexCreaterCreator.create();
        IndexReader reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
        ArrayList<DescriptorWeight> descriptorWeights = new ArrayList<DescriptorWeight>();
        descriptorWeights.add(new DescriptorWeight("EdgeHistogram", 6f));
        descriptorWeights.add(new DescriptorWeight("CEDD", 3f));
//        descriptorWeights.add(new DescriptorWeight("FCTH", 0.5f));
//        descriptorWeights.add(new DescriptorWeight("ColorLayout", 2f));
//        descriptorWeights.add(new DescriptorWeight("ColorLayout", 2f));
//        descriptorWeights.add(new DescriptorWeight("JCD", 0.4f));
//        descriptorWeights.add(new DescriptorWeight("Tamura", 0.3f));
//        descriptorWeights.add(new DescriptorWeight("ScalableColor",0.3f));
//        descriptorWeights.add(new DescriptorWeight("AutoColorCorrelogram",0.3f));
//        descriptorWeights.add(new DescriptorWeight("Gabor",0.3f));
//        descriptorWeights.add(new DescriptorWeight("SimpleColorHistogram",0.3f));
//        descriptorWeights.add(new DescriptorWeight("JpegCoefficientHistogram",0.3f));
//
        ImageSearcher searcher = ImageSearcherFactory.createChainedSearcher(5, descriptorWeights);
        FileInputStream imageStream = new FileInputStream(testFilePath + "1.jpg");
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
