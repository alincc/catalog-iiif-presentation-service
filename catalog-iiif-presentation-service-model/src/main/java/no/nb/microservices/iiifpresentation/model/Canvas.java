package no.nb.microservices.iiifpresentation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"context", "id", "type", "label", "height", "width"})
public class Canvas {
    @JsonProperty("@context")
    private String context;

    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String label;
    private int width;
    private int height;
    private List<Annotation> images;

    @JsonCreator
    public Canvas() {
        super();
        this.type = "sc:Canvas";
    }
    
    public Canvas(String id, String label, int width, int height, List<Annotation> images) {
        this();
        this.id = id;
        this.label = label;
        this.width = width;
        this.height = height;
        this.images = images;
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

    public List<Annotation> getImages() {
        return images;
    }

}
