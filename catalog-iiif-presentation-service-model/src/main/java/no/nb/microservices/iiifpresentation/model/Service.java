package no.nb.microservices.iiifpresentation.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "context","id", "protocol", "width", "height", "sizes", "tiles", "profile" })
public class Service {
    @JsonProperty("@context")
    private String context;
    @JsonProperty("@id")
    private String id;
    private String protocol;
    private List<String> profile;
    private int width;
    private int height;
    private List<Size> sizes;
    private List<TileInfo> tiles;
    private double physicalScale;
    
    public Service() {
        super();
        this.context = "http://iiif.io/api/image/1/context.json";
        this.protocol = "http://iiif.io/api/image";
    }

    public Service(String id, int width, int height, double physicalScale) {
        this();
        this.id = id;
        this.width = width;
        this.height = height;
        this.physicalScale = physicalScale;
    }

    public double getPhysicalScale() {
        return physicalScale;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProfile() {
        if (profile == null) {
            profile = Arrays.asList("http://library.stanford.edu/iiif/image-api/1.1/compliance.html#level0"); 
        }
        return profile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getProtocol() {
        return protocol;
    }

    public List<Size> getSizes() {
        if (sizes == null) {
            int levels = getNumberOfLevels(width, height);
            sizes = createSizes(levels);
        }
        return sizes;
    }

    public List<TileInfo> getTiles() {
        if (tiles == null) {
            List<Integer> scaleFactors = getScaleFactors();
            tiles = new ArrayList<>();
            tiles.add(new TileInfo(1024, null, scaleFactors));
        }
        return tiles;
    }

    private List<Integer> getScaleFactors() {
        List<Integer> scaleFactors = new ArrayList<>();
        int numberOfLevels = getNumberOfLevels(width, height);
        for(int i = 0; i < numberOfLevels; i++) {
            scaleFactors.add(1<<i);
        }
        return scaleFactors;
    }    
    
    private int getNumberOfLevels(int w, int h) {
        int l = Math.max(w, h);
        int m = 96;
        int r = 0;
        int i;
        if (l > 0) {
            for (i = 1; l >= m; i++) {
                l = l / 2;
                r = i;
            }
        }
        return r;
    }

    private List<Size> createSizes(int levels) {
        List<Size> sizes = new ArrayList<>();
        int levelWidth = width;
        int levelHeight = height;
        for(int level = 1; level < levels; level++) {
            levelWidth = levelWidth/2;
            levelHeight = levelHeight/2;
            Size size = new Size(levelWidth, levelHeight);
            sizes.add(size);
        }
        return sizes;
    }
    
}