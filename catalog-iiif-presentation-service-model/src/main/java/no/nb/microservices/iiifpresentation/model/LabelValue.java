package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andreasb on 11.06.15.
 */
public class LabelValue {
    private String label;
    private Object value;

    @JsonCreator
    public LabelValue(@JsonProperty("label") String label, @JsonProperty("value") Object value) {
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
