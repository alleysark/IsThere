package org.isthere;

/**
 * 이써에서 사용 될 Document.
 *
 * User: Hyunje
 * Date: 12. 11. 16
 * Time: 오후 4:25
 * To change this template use File | Settings | File Templates.
 */
public class IsThereDocument {
    private String values;

    public IsThereDocument() {
        values = "";
    }

    public IsThereDocument(String values) {
        this.values = values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void setValues(IsThereDocument doc) {
        this.values = doc.getValues();
    }

    public String getValues() {
        return values;
    }
}
