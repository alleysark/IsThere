package isthere.local.test;

import net.semanticmetadata.lire.imageanalysis.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.CEDD;
import net.semanticmetadata.lire.imageanalysis.LireFeature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Test class for inheritance ofLireFeature and each descriptors.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 21
 *        Time: 오전 11:20
 */
public class LireFeatureArrayListTest {
    static ArrayList<LireFeature> features;
    static BufferedImage image;
    private static String testFilePath = "D:\\Importants\\Test_images\\";

    public static void main(String[] args) throws IOException {
        FileInputStream imageStream = new FileInputStream(testFilePath + "8.jpg");
        BufferedImage bimg = ImageIO.read(imageStream);

        System.out.println(testFilePath + "8.jpg");

        features = new ArrayList<LireFeature>();
        inputDescriptors(bimg);
        printArray();
    }

    private static void inputDescriptors(BufferedImage img) {
        AutoColorCorrelogram auto = new AutoColorCorrelogram();
        auto.extract(img);
        CEDD cedd = new CEDD();
        cedd.extract(img);
        features.add(auto);
        features.add(cedd);
    }

    private static void printArray() {
        for (LireFeature element : features) {
            System.out.println(element.getClass().getSimpleName());
        }
    }
}
