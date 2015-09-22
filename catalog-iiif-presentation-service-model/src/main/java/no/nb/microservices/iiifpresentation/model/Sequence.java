package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "type", "label"})
public class Sequence {
    @JsonProperty("@id")
    private String id;
    
    @JsonProperty("@type")
    private String type;
    
    private String label;

    @JsonCreator
    public Sequence() {
        super();
        this.type = "sc:Sequence";
        this.label = "Current Page Order";
    }
    
    public Sequence(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

}
