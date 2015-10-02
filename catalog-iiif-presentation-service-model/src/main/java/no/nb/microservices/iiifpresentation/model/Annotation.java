package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"context", "id", "type", "motivation", "resource", "on"})
public class Annotation {
    @JsonProperty("@context")
    private String context;
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private String motivation;
    private Resource resource;
    private String on;

    public Annotation() {
        super();
        this.type = "oa:Annotation";
        this.motivation = "sc:painting";
    }
    
    public Annotation(Context context, String id,
            String on, Resource resource) {
        this();
        this.context = context.getContext();
        this.id = id;
        this.on = on;
        this.resource = resource;
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

    public String getMotivation() {
        return motivation;
    }

    public Resource getResource() {
        return resource;
    }

    public String getOn() {
        return on;
    }

}
