package org.isthere;

import net.semanticmetadata.lire.imageanalysis.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.EdgeHistogram;
import net.semanticmetadata.lire.imageanalysis.FCTH;
import net.semanticmetadata.lire.imageanalysis.LireFeature;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 이써에서 쓰일 StringIndexer 의 각 이미지의 정보를 담고 있을 Document의 Builder.
 * 사용할 알고리즘에 따라 생성자 부분을 수정해야 한다.
 * User: Hyunje
 * Date: 12. 11. 16
 * Time: 오전 6:28
 */
public class IsThereDocumentBuilder {
    private ArrayList<LireFeature> selectedFeatures;
    String mainDelim;
    String semiDelim;

    public IsThereDocumentBuilder(String mainDelim, String semiDelim) {
        selectedFeatures = new ArrayList<LireFeature>();
        this.mainDelim = mainDelim;
        this.semiDelim = semiDelim;
        selectedFeatures.add(new AutoColorCorrelogram());
        selectedFeatures.add(new EdgeHistogram());
        selectedFeatures.add(new FCTH());

    }

    public synchronized  IsThereDocument createDocument(BufferedImage image, String imageIdentifier) {
        IsThereDocument doc = new IsThereDocument();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(imageIdentifier + mainDelim);
        for (LireFeature feature : selectedFeatures) {
            feature.extract(image);
            stringBuilder.append(feature.getStringRepresentation() + semiDelim);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n");
        doc.setValues(stringBuilder.toString());
        return doc;
    }
}
