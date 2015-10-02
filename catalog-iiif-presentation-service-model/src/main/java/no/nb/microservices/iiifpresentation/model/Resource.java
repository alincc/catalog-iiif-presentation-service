package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "type", "format", "service", "height", "width"})
public class Resource {
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String format;
    private Service service;
    private int width;
    private int height;
    
    public Resource() {
        super();
        this.type = "dctypes:Image";
        this.format = "image/jpeg";
    }

    public Resource(String id, int width,
            int height, Service service) {
        this();
        this.id = id;
        this.width = width;
        this.height = height;
        this.service = service;
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

    public Service getService() {
        return service;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
