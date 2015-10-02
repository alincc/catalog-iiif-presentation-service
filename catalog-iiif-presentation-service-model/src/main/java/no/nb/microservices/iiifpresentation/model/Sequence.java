package no.nb.microservices.iiifpresentation.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"context", "id", "type", "label", "canvases"})
public class Sequence {
    @JsonProperty("@context")
    private String context;
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
    
    public Sequence(Context context, String id) {
        this();
        this.context = context.getContext();
        this.id = id;
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

    public List<Canvas> getCanvases() {
        return canvases;
    }
    
    public void addCanvas(Canvas canvas) {
        canvases.add(canvas);
    }

}
