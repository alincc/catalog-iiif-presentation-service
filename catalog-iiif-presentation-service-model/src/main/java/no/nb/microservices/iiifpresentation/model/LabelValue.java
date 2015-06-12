package no.nb.microservices.iiifpresentation.model;

/**
 * Created by andreasb on 11.06.15.
 */
public class LabelValue {
    private String label;
    private Object value;

    public LabelValue(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
