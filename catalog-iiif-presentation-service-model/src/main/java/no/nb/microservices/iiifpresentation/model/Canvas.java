package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "type", "label", "height", "width"})
public class Canvas {
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String label;
    private int width;
    private int height;

    @JsonCreator
    public Canvas() {
        super();
        this.type = "sc:Canvas";
    }
    
    public Canvas(String id, String label, int width, int height) {
        this();
        this.id = id;
        this.label = label;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
