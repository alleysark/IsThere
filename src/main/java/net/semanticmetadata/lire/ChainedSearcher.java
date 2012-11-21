package net.semanticmetadata.lire;

import extra166y.Ops;
import extra166y.ParallelArray;
import jsr166y.ForkJoinPool;
import net.semanticmetadata.lire.imageanalysis.*;
import net.semanticmetadata.lire.impl.SimpleImageDuplicates;
import net.semanticmetadata.lire.impl.SimpleImageSearchHits;
import net.semanticmetadata.lire.impl.SimpleResult;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * ChainedSearcher generates a chained searcher. This class is came from SimpleImageSearcher(duplicated).
 * Chained searcher have to provides follow properties
 * <br>    1. Have to allocate proper weights for each descriptor.</br>
 * <br>    2. Number of descriptors is variable.</br>
 * <br>    3. Need to check existed fields inclusion of input descriptors.</br>
 * <p>This searcher controls follow descriptors </p>
 * <br>AutoColorCorrelogram</br>
 * <br>CEDD</br>
 * <br>ColorLayout</br>
 * <br>EdgeHistogram</br>
 * <br>FCTH</br>
 * <br>Gabor</br>
 * <br>JCD</br>
 * <br>JpegCoefficientHistogram</br>
 * <br>ScalableColor</br>
 * <br>Tamura</br>
 * <br>(Optional)SIFT or SURF</br>
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 19
 *        Time: 오후 11:39
 */
public class ChainedSearcher extends AbstractImageSearcher {
    private int maxHits = 2;
    /*
        private float AutoColorCorrelogramWeight=1f;
        private float CEDDWeight=1f;
        private float ColorLayoutWeight=1f;
        private float EdgeHistogramWeight=1f;
        private float FCTHWeight=1f;
        private float GaborWeight=1f;
        private float JCDWeight=1f;
        private float JpegCoefficientHistogramWeight=1f;
        private float ScalableColorWeight=1f;
        private float TamuraWeight=1f;
        private float SIFTWeight=1f;
        private float SimpleColorHistogram=1f;
    */
    private HashMap<String, Float> descriptor_weightMap;

    private TreeSet<SimpleResult> docs;

    public ChainedSearcher(int maxHits, ArrayList<DescriptorWeight> descriptorWeights) {
        this.maxHits = maxHits;
        descriptor_weightMap = new HashMap<String, Float>();
        docs = new TreeSet<SimpleResult>();
        for (DescriptorWeight element : descriptorWeights) {
            if (CheckDescriptorAndWeight(element))
                descriptor_weightMap.put(element.descriptor.toUpperCase(), element.weight);
        }
    }

    private boolean CheckDescriptorAndWeight(DescriptorWeight element) {
        if (!("AutoColorCorrelogram".equals(element.descriptor)) &&
            !("CEDD".equals(element.descriptor)) &&
            !("ColorLayout".equals(element.descriptor)) &&
            !("EdgeHistogram".equals(element.descriptor)) &&
            !("FCTH".equals(element.descriptor)) &&
            !("Gabor".equals(element.descriptor)) &&
            !("JCD".equals(element.descriptor)) &&
            !("JpegCoefficientHistogram".equals(element.descriptor)) &&
            !("ScalableColor".equals(element.descriptor)) &&
            !("Tamura".equals(element.descriptor)) &&
            !("SimpleColorHistogram".equals(element.descriptor)) &&
            !("SIFT".equals(element.descriptor)) &&
            !("SURF".equals(element.descriptor))) {
            System.out.println("Invalid Descriptor : " + element.descriptor);
            return false;
        }
        if (element.weight < 0f) {
            System.out.println("Invalid Weight : " + element.weight);
            return false;
        }
        return true;

    }

    /**
     * Search with descriptors. Checks for each descriptor
     *
     * @param image  the example image to search for.
     * @param reader the IndexReader which is used to dsearch through the images.
     */
    @Override
    public ImageSearchHits search(BufferedImage image, IndexReader reader) {
        ArrayList<LireFeature> selectedFeatures = new ArrayList<LireFeature>();
        AutoColorCorrelogram autoColorCorrelogram;

        if (descriptor_weightMap.get("autoColorCorrelogram".toUpperCase()) != null)
            if (descriptor_weightMap.get("autoColorCorrelogram".toUpperCase()) > 0f) {
                autoColorCorrelogram = new AutoColorCorrelogram();
                autoColorCorrelogram.extract(image);
                selectedFeatures.add(autoColorCorrelogram);
            }

        CEDD cedd;
        if (descriptor_weightMap.get("cedd".toUpperCase()) != null)
            if (descriptor_weightMap.get("cedd".toUpperCase()) > 0f) {
                cedd = new CEDD();
                cedd.extract(image);
                selectedFeatures.add(cedd);
            }

        ColorLayout colorLayout;
        if (descriptor_weightMap.get("colorLayout".toUpperCase()) != null)
            if (descriptor_weightMap.get("colorLayout".toUpperCase()) > 0f) {
                colorLayout = new ColorLayout();
                colorLayout.extract(image);
                selectedFeatures.add(colorLayout);
            }

        EdgeHistogram edgeHistogram;
        if (descriptor_weightMap.get("edgeHistogram".toUpperCase()) != null)
            if (descriptor_weightMap.get("edgeHistogram".toUpperCase()) > 0f) {
                edgeHistogram = new EdgeHistogram();
                edgeHistogram.extract(image);
                selectedFeatures.add(edgeHistogram);
            }

        FCTH ftch;
        if (descriptor_weightMap.get("fcth".toUpperCase()) != null)
            if (descriptor_weightMap.get("fcth".toUpperCase()) > 0f) {
                ftch = new FCTH();
                ftch.extract(image);
                selectedFeatures.add(ftch);
            }

        Gabor gabor;
        if (descriptor_weightMap.get("gabor".toUpperCase()) != null)
            if (descriptor_weightMap.get("gabor".toUpperCase()) > 0f) {
                gabor = new Gabor();
                gabor.extract(image);
                selectedFeatures.add(gabor);
            }

        JCD jcd;
        if (descriptor_weightMap.get("jcd".toUpperCase()) != null)
            if (descriptor_weightMap.get("JCD".toUpperCase()) > 0f) {
                jcd = new JCD();
                jcd.extract(image);
                selectedFeatures.add(jcd);
            }

        JpegCoefficientHistogram jpegCoefficientHistogram;
        if (descriptor_weightMap.get("jpegCoefficientHistogram".toUpperCase()) != null)
            if (descriptor_weightMap.get("jpegCoefficientHistogram".toUpperCase()) > 0f) {
                jpegCoefficientHistogram = new JpegCoefficientHistogram();
                jpegCoefficientHistogram.extract(image);
                selectedFeatures.add(jpegCoefficientHistogram);
            }

        ScalableColor scalableColor;
        if (descriptor_weightMap.get("scalableColor".toUpperCase()) != null)
            if (descriptor_weightMap.get("scalableColor".toUpperCase()) > 0f) {
                scalableColor = new ScalableColor();
                scalableColor.extract(image);
                selectedFeatures.add(scalableColor);
            }

        Tamura tamura;
        if (descriptor_weightMap.get("tamura".toUpperCase()) != null)
            if (descriptor_weightMap.get("tamura".toUpperCase()) > 0f) {
                tamura = new Tamura();
                tamura.extract(image);
                selectedFeatures.add(tamura);
            }

        SimpleColorHistogram simpleColorHistogram;
        if (descriptor_weightMap.get("SimpleColorHistogram".toUpperCase()) != null)
            if (descriptor_weightMap.get("SimpleColorHistogram".toUpperCase()) > 0f) {
                simpleColorHistogram = new SimpleColorHistogram();
                simpleColorHistogram.extract(image);
                selectedFeatures.add(simpleColorHistogram);
            }

        float maxDistance = 0f;
        try {
            maxDistance = findSimilarParallel(reader, selectedFeatures);
//            maxDistance = findSimilar(reader, selectedFeatures);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new SimpleImageSearchHits(this.docs, maxDistance);
    }

    private float findSimilarParallel(IndexReader reader, final ArrayList<LireFeature> selectedFeatures) throws Exception {
        boolean hasDeletions = reader.hasDeletions();
        final float maxDistance = -1f, overallMaxDistance = -1f;

        docs.clear();

        int numDocs = reader.numDocs();
        Document[] documents = new Document[numDocs];
        for (int i = 0; i < numDocs; i++) {
            documents[i] = reader.document(i);
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(20);
        ParallelArray<Document> students = ParallelArray.createUsingHandoff(documents, forkJoinPool);
        final TreeSet<SimpleResult> docs = new TreeSet<SimpleResult>();
        students.withMapping(new Ops.ObjectToDouble<Document>() {
            float maxDistance = -1f;
            float overallMaxDistance = -1f;
            float distance = -1f;
            public double op(Document d) {

                try {
                    distance = getDistance(d, selectedFeatures);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (overallMaxDistance < distance) {
                    overallMaxDistance = distance;
                }

                if (maxDistance < 0) {
                    maxDistance = distance;
                }

                if (docs.size() < maxHits) {
                    docs.add(new SimpleResult(distance, d));
                    if (distance > maxDistance) maxDistance = distance;
                } else if (distance < maxDistance) {
                    docs.remove(docs.last());
                    docs.add(new SimpleResult(distance, d));
                    maxDistance = docs.last().getDistance();
                }
                return distance;
            }
        }).sum();
        this.docs = docs;
        return docs.last().getDistance();
    }

    private float findSimilar(IndexReader reader, ArrayList<LireFeature> selectedFeatures) throws IOException {
        float maxDistance = -1f, overallMaxDistance = -1f;
        boolean hasDeletions = reader.hasDeletions();

        //clear result set
        docs.clear();

        int numDocs = reader.numDocs();
        for (int i = 0; i < numDocs; i++) {
            if (hasDeletions && reader.isDeleted(i)) {
                continue;
            }

            Document d = reader.document(i);
            float distance = 0; //returns distance for selected features.
            try {
                distance = getDistance(d, selectedFeatures);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (overallMaxDistance < distance) {
                overallMaxDistance = distance;
            }
            //for first document
            if (maxDistance < 0) {
                maxDistance = distance;
            }
            //if the array is not full yet
            if (this.docs.size() < maxHits) {
                this.docs.add(new SimpleResult(distance, d));
                if (distance > maxDistance) maxDistance = distance;
            } else if (distance < maxDistance) {
                //TODO:
                // if it is nearer to the sample than at least on of the current set:
                // remove the last one ...
                this.docs.remove(this.docs.last());
                // add the new one ...
                this.docs.add(new SimpleResult(distance, d));
                // and set our new distance border ...
                maxDistance = this.docs.last().getDistance();
            }
        }
        System.out.println(maxDistance);
        return maxDistance;
    }

    private float getDistance(Document d, ArrayList<LireFeature> selectedFeatures) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        float distance = 0f;
        int descriptorCount = 0;

        for (LireFeature descriptor : selectedFeatures) {
            String descriptorField = getFieldName(descriptor);

            //TODO: Check here.
            String[] values = d.getValues(descriptorField);
            if (values != null && values.length > 0) {
                Class curdes = Class.forName(descriptor.getClass().getName());
                LireFeature feature = (LireFeature) curdes.newInstance();
                feature.setStringRepresentation(values[0]);
//                System.out.println("Current Descriptor : " + descriptor.getClass().getSimpleName());
                distance += descriptor.getDistance(feature) * descriptor_weightMap.get(descriptor.getClass().getSimpleName().toUpperCase());
                descriptorCount++;
            } else if (values != null && values.length == 0) {
                byte[] tempBinaryValue = d.getBinaryValue(descriptorField);
                if (tempBinaryValue != null && tempBinaryValue.length > 0) {
                    Class curdes = Class.forName(descriptor.getClass().getName());
                    LireFeature feature = (LireFeature) curdes.newInstance();
                    feature.setByteArrayRepresentation(tempBinaryValue);
                    distance += descriptor.getDistance(feature) * descriptor_weightMap.get(descriptor.getClass().getSimpleName().toUpperCase());
                    descriptorCount++;
                }
            } else {
                System.out.println("Get Distance Error!");
            }
        }

        if (descriptorCount > 0) {
            distance = distance / (float) descriptorCount;
        }
        return distance;
    }

    private String getFieldName(LireFeature descriptor) {
        if ("ColorLayout".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_COLORLAYOUT;
        if ("ScalableColor".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_SCALABLECOLOR;
        if ("EdgeHistogram".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_EDGEHISTOGRAM;
        if ("AutoColorCorrelogram".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_AUTOCOLORCORRELOGRAM;
        if ("CEDD".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_CEDD;
        if ("FCTH".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_FCTH;
        if ("JCD".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_JCD;
        if ("Tamura".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_TAMURA;
        if ("Gabor".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_GABOR;
        if ("JpegCoefficientHistogram".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_JPEGCOEFFS;
        if ("SIFT".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_SIFT;
        if ("SimpleColorHistogram".toUpperCase().equals(descriptor.getClass().getSimpleName().toUpperCase()))
            return DocumentBuilder.FIELD_NAME_COLORHISTOGRAM;
        return null;
    }

    @Override
    public ImageSearchHits search(Document doc, IndexReader reader) {
        ArrayList<LireFeature> selectedFeatures = new ArrayList<LireFeature>();
        AutoColorCorrelogram autoColorCorrelogram;
        String[] autoColors = doc.getValues(DocumentBuilder.FIELD_NAME_AUTOCOLORCORRELOGRAM);
        if (autoColors != null && autoColors.length > 0) {
            autoColorCorrelogram = new AutoColorCorrelogram();
            autoColorCorrelogram.setStringRepresentation(autoColors[0]);
            selectedFeatures.add(autoColorCorrelogram);
        }

        CEDD cedd;
        String[] cedds = doc.getValues(DocumentBuilder.FIELD_NAME_CEDD);
        if (cedds != null && cedds.length > 0) {
            cedd = new CEDD();
            cedd.setStringRepresentation(cedds[0]);
            selectedFeatures.add(cedd);
        }

        ColorLayout colorLayout;
        String[] cLayouts = doc.getValues(DocumentBuilder.FIELD_NAME_COLORLAYOUT);
        if (cLayouts != null && cLayouts.length > 0) {
            colorLayout = new ColorLayout();
            colorLayout.setStringRepresentation(cLayouts[0]);
            selectedFeatures.add(colorLayout);
        }

        EdgeHistogram edgeHistogram;
        String[] eHs = doc.getValues(DocumentBuilder.FIELD_NAME_EDGEHISTOGRAM);
        if (eHs != null && eHs.length > 0) {
            edgeHistogram = new EdgeHistogram();
            edgeHistogram.setStringRepresentation(eHs[0]);
            selectedFeatures.add(edgeHistogram);
        }

        FCTH ftch;
        String[] fts = doc.getValues(DocumentBuilder.FIELD_NAME_FCTH);
        if (fts != null && fts.length > 0) {
            ftch = new FCTH();
            ftch.setStringRepresentation(fts[0]);
            selectedFeatures.add(ftch);
        }

        Gabor gabor;
        String[] gs = doc.getValues(DocumentBuilder.FIELD_NAME_GABOR);
        if (gs != null && gs.length > 0) {
            gabor = new Gabor();
            gabor.setStringRepresentation(gs[0]);
            selectedFeatures.add(gabor);
        }

        JCD jcd;
        String[] js = doc.getValues(DocumentBuilder.FIELD_NAME_JCD);
        if (js != null && js.length > 0) {
            jcd = new JCD();
            jcd.setStringRepresentation(js[0]);
            selectedFeatures.add(jcd);
        }

        JpegCoefficientHistogram jpegCoefficientHistogram;
        String[] jps = doc.getValues(DocumentBuilder.FIELD_NAME_JPEGCOEFFS);
        if (jps != null && jps.length > 0) {
            jpegCoefficientHistogram = new JpegCoefficientHistogram();
            jpegCoefficientHistogram.setStringRepresentation(jps[0]);
            selectedFeatures.add(jpegCoefficientHistogram);
        }

        ScalableColor scalableColor;
        String[] scas = doc.getValues(DocumentBuilder.FIELD_NAME_SCALABLECOLOR);
        if (scas != null && scas.length > 0) {
            scalableColor = new ScalableColor();
            scalableColor.setStringRepresentation(scas[0]);
            selectedFeatures.add(scalableColor);
        }

        Tamura tamura;
        String[] ts = doc.getValues(DocumentBuilder.FIELD_NAME_TAMURA);
        if (ts != null && ts.length > 0) {
            tamura = new Tamura();
            tamura.setStringRepresentation(ts[0]);
            selectedFeatures.add(tamura);
        }
        float maxDistance = 0f;
        try {
            maxDistance = findSimilar(reader, selectedFeatures);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SimpleImageSearchHits(this.docs, maxDistance);
    }

    @Override
    public ImageDuplicates findDuplicates(IndexReader reader) throws IOException {
        //get the first document
        if (!IndexReader.indexExists(reader.directory()))
            throw new FileNotFoundException("No index found at this specific loaction.");
        Document doc = reader.document(0);

        ArrayList<LireFeature> selectedFeatures = new ArrayList<LireFeature>();
        AutoColorCorrelogram autoColorCorrelogram;
        String[] autoColors = doc.getValues(DocumentBuilder.FIELD_NAME_AUTOCOLORCORRELOGRAM);
        if (autoColors != null && autoColors.length > 0) {
            autoColorCorrelogram = new AutoColorCorrelogram();
            autoColorCorrelogram.setStringRepresentation(autoColors[0]);
            selectedFeatures.add(autoColorCorrelogram);
        }

        CEDD cedd;
        String[] cedds = doc.getValues(DocumentBuilder.FIELD_NAME_CEDD);
        if (cedds != null && cedds.length > 0) {
            cedd = new CEDD();
            cedd.setStringRepresentation(cedds[0]);
            selectedFeatures.add(cedd);
        }

        ColorLayout colorLayout;
        String[] cLayouts = doc.getValues(DocumentBuilder.FIELD_NAME_COLORLAYOUT);
        if (cLayouts != null && cLayouts.length > 0) {
            colorLayout = new ColorLayout();
            colorLayout.setStringRepresentation(cLayouts[0]);
            selectedFeatures.add(colorLayout);
        }

        EdgeHistogram edgeHistogram;
        String[] eHs = doc.getValues(DocumentBuilder.FIELD_NAME_EDGEHISTOGRAM);
        if (eHs != null && eHs.length > 0) {
            edgeHistogram = new EdgeHistogram();
            edgeHistogram.setStringRepresentation(eHs[0]);
            selectedFeatures.add(edgeHistogram);
        }

        FCTH ftch;
        String[] fts = doc.getValues(DocumentBuilder.FIELD_NAME_FCTH);
        if (fts != null && fts.length > 0) {
            ftch = new FCTH();
            ftch.setStringRepresentation(fts[0]);
            selectedFeatures.add(ftch);
        }

        Gabor gabor;
        String[] gs = doc.getValues(DocumentBuilder.FIELD_NAME_GABOR);
        if (gs != null && gs.length > 0) {
            gabor = new Gabor();
            gabor.setStringRepresentation(gs[0]);
            selectedFeatures.add(gabor);
        }

        JCD jcd;
        String[] js = doc.getValues(DocumentBuilder.FIELD_NAME_JCD);
        if (js != null && js.length > 0) {
            jcd = new JCD();
            jcd.setStringRepresentation(js[0]);
            selectedFeatures.add(jcd);
        }

        JpegCoefficientHistogram jpegCoefficientHistogram;
        String[] jps = doc.getValues(DocumentBuilder.FIELD_NAME_JPEGCOEFFS);
        if (jps != null && jps.length > 0) {
            jpegCoefficientHistogram = new JpegCoefficientHistogram();
            jpegCoefficientHistogram.setStringRepresentation(jps[0]);
            selectedFeatures.add(jpegCoefficientHistogram);
        }

        ScalableColor scalableColor;
        String[] scas = doc.getValues(DocumentBuilder.FIELD_NAME_SCALABLECOLOR);
        if (scas != null && scas.length > 0) {
            scalableColor = new ScalableColor();
            scalableColor.setStringRepresentation(scas[0]);
            selectedFeatures.add(scalableColor);
        }

        Tamura tamura;
        String[] ts = doc.getValues(DocumentBuilder.FIELD_NAME_TAMURA);
        if (ts != null && ts.length > 0) {
            tamura = new Tamura();
            tamura.setStringRepresentation(ts[0]);
            selectedFeatures.add(tamura);
        }

        HashMap<Float, List<String>> duplicates = new HashMap<Float, List<String>>();
        boolean hasDeletions = reader.hasDeletions();

        int numDocs = reader.numDocs();
        int numDuplicates = 0;
        for (int i = 0; i < numDocs; i++) {
            if (hasDeletions && reader.isDeleted(i)) {
                continue;
            }
            Document d = reader.document(i);
            float distance = 0;

            try {
                distance = getDistance(d, selectedFeatures);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            if (!duplicates.containsKey(distance)) {
                duplicates.put(distance, new LinkedList<String>());
            } else {
                numDuplicates++;
            }
            //TODO: Check this getField method.
            duplicates.get(distance).add(d.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
        }

        if (numDuplicates == 0) return null;

        LinkedList<List<String>> results = new LinkedList<List<String>>();
        for (float f : duplicates.keySet()) {
            if (duplicates.get(f).size() > 1) {
                results.add(duplicates.get(f));
            }
        }

        return new SimpleImageDuplicates(results);
    }
}
