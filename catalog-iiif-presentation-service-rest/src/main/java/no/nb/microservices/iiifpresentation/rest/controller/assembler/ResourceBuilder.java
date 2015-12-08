package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.model.Service;

public class ResourceBuilder {

    private String imageId;
    private int width;
    private int height;
    private int scanResolution;
    
    public ResourceBuilder withImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }
    
    public ResourceBuilder withWidth(int width) {
        this.width = width;
        return this;
    }
    
    public ResourceBuilder withHeight(int height) {
        this.height = height;
        return this;
    }

    public ResourceBuilder withScanResolution(int scanResolution) {
        this.scanResolution = scanResolution;
        return this;
    }

    public Resource build() {
        String id = new IiifImageServerUrlBuilder()
                .withIdentifer(imageId)
                .toString();
        
        Service physicalService = new ServiceBuilder()
                .withContext("http://iiif.io/api/annex/service/physdim/1/context.json")
                .withProfile("http://iiif.io/api/annex/service/physdim")
                .withPhysicalScale(getphysicalScale(width, scanResolution))
                .withPhysicalUnits("in")
                .build();
        
        Service service = new ServiceBuilder()
            .withContext("http://iiif.io/api/image/2/context.json")
            .withProtocol("http://iiif.io/api/image")
            .withIdentifier(imageId)
            .withProfile("http://iiif.io/api/image/2/level1.json")
            .withWidth(width)
            .withHeight(height)
            .withService(physicalService)
            .build();
        return new Resource(id, width, height, service);
    }
    
    private double getphysicalScale(int width, int scanResolution) {
        return ((double) width / (double) scanResolution) / (double) width;
    }

    
}
