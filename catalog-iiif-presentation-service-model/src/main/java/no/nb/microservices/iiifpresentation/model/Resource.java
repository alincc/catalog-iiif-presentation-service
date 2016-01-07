package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "type", "format", "service", "height", "width"})
public class Resource {
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String format;
    private Service service;
    private Integer width;
    private Integer height;
    private String description;

    public Resource() {
        super();
    }
    
    public Resource(String id, String type, String format, Integer width,
            Integer height, String description, Service service) {
        this();
        this.id = id;
        this.type = type;
        this.format = format;
        this.width = width;
        this.height = height;
        this.description = description;
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
    
    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }

}
