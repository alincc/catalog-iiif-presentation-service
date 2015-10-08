package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.model.Service;

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
        String id = new IiifImageServerUrlBuilder()
                .withIdentifer(imageId)
                .toString();
        Service service = new ServiceBuilder()
            .withIdentifier(imageId)
            .withWidth(width)
            .withHeight(height)
            .build();
        return new Resource(id, width, height, service);
    }
}
