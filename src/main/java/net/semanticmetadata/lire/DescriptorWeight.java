package net.semanticmetadata.lire;

/**
 * Class for descriptor-weight pair.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 20
 *        Time: 오전 12:35
 */
public class DescriptorWeight {
    String descriptor;
    float weight = 1f;

    public DescriptorWeight(String descriptor, float weight) {
        this.descriptor = descriptor;
        this.weight = weight;
    }

    public DescriptorWeight(String descriptor) {
        this.descriptor = descriptor;
        this.weight = 1.0f;
    }
}
