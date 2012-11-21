package net.semanticmetadata.lire.imageanalysis;

import net.semanticmetadata.lire.imageanalysis.sift.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for SIFT.
 * Trying to make documentbuilder for SIFT now.
 *
 * @author Hyunje
 * @since 1.0
 *        Time: 오후 8:19
 */
public class SIFT implements LireFeature {
    int steps = 3;
    float initial_sigma = 1.6f;
    double bg = 0.0;
    int fdsize = 4;
    int fdbins = 8;
    int min_size = 64;
    int max_size = 1024;
    float min_epsilon = 2.0f;
    float max_epsilon = 100.0f;
    float min_inlier_ratio = 0.05f;
    float scale = 1.0f;

    LinkedList<Feature> data;

    public SIFT() {
        data = new LinkedList<Feature>();
    }

    @Override
    public void extract(BufferedImage bimg) {
        try {
            data = (LinkedList<Feature>) computeSIFTFeatures(bimg);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public byte[] getByteArrayRepresentation() {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setByteArrayRepresentation(byte[] in) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double[] getDoubleHistogram() {
        return new double[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getDistance(LireFeature feature) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getStringRepresentation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStringRepresentation(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<Feature> computeSIFTFeatures(BufferedImage img) throws IOException {
        LinkedList<Feature> fs = new LinkedList<Feature>();
        FloatArray2DSIFT sift = new FloatArray2DSIFT(fdsize, fdbins);
        FloatArray2D fa = ImageArrayConverter.ImageToFloatArray2D(img);
        Filter.enhance(fa, 1.0f);
        sift.init(fa, steps, initial_sigma, min_size, max_size);
        fs.addAll(sift.run(max_size));
        Collections.sort(fs);
        return fs;
    }
}
