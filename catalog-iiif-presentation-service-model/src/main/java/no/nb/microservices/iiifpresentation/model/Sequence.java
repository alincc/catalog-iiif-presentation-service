package no.nb.microservices.iiifpresentation.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<Canvas> canvases;

    @JsonCreator
    public Sequence() {
        super();
        this.type = "sc:Sequence";
        this.label = "Current Page Order";
        this.canvases = new ArrayList<>();
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

    public List<Canvas> getCanvases() {
        return canvases;
    }
    
    public void addCanvas(Canvas canvas) {
        canvases.add(canvas);
    }

}
