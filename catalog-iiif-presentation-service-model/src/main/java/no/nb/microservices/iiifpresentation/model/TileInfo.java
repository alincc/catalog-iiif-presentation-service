package no.nb.microservices.iiifpresentation.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "width","height", "scalefactors" })
public class TileInfo {

    private Integer width;
    private Integer height;
    private List<Integer> scaleFactors;
    
    public TileInfo() {
        super();
        this.scaleFactors = new ArrayList<>();
    }
    
    public TileInfo(Integer width, Integer height, List<Integer> scaleFactors) {
        this();
        this.width = width;
        this.height = height;
        this.scaleFactors = scaleFactors;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Integer> getScaleFactors() {
        return scaleFactors;
    }

}
