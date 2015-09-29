package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"context", "id", "type", "motivation", "on"})
public class Resource {
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String format;
    private int width;
    private int height;
    
    public Resource() {
        super();
        this.type = "dctypes:Image";
        this.format = "image/jpeg";
    }

    public Resource(String id, int width,
            int height) {
        this();
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
