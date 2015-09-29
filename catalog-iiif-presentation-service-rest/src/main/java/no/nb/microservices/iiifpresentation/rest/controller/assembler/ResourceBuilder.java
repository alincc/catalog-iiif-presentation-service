package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Resource;

public class ResourceBuilder {

    private String imageId;
    private int width;
    private int height;
    
    public ResourceBuilder withImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }
    
    public ResourceBuilder withWidth(int width) {
        this.width = width;
        return this;
    }
    
    public ResourceBuilder widthHeight(int height) {
        this.height = height;
        return this;
    }
    
    public Resource build() {
        String iiifImageServer = "http://www.nb.no/services/iiif/api/";
        String id = iiifImageServer + imageId;
        
        return new Resource(id, width, height);
    }
}
