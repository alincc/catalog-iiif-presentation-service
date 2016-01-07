package no.nb.microservices.iiifpresentation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    private List<Object> otherContent;

    @JsonCreator
    public Canvas() {
        super();
        this.type = "sc:Canvas";
    }
    
    public Canvas(Context context, String id, String label, int width, int height, List<Annotation> images, List<Object> otherContent) {
        this();
        this.context = context.getContext();
        this.id = id;
        this.label = label;
        this.width = width;
        this.height = height;
        this.images = images;
        this.otherContent = otherContent;
    }
    
    public String getContext() {
        return context;
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

    public List<Object> getOtherContent() {
        return otherContent;
    }
}
