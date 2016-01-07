package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.model.Service;

public class ResourceBuilder {

    private String id;
    private String type;
    private String format;
    private Integer width;
    private Integer height;
    private int scanResolution;
    private String description;
    
    public ResourceBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ResourceBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ResourceBuilder withFormat(String format) {
        this.format = format;
        return this;
    }
    
    public ResourceBuilder withWidth(Integer width) {
        this.width = width;
        return this;
    }
    
    public ResourceBuilder withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public ResourceBuilder withScanResolution(int scanResolution) {
        this.scanResolution = scanResolution;
        return this;
    }

    public ResourceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Resource build() {
        Service service = null;
        if (width != null && height != null) {
            Service physicalService = new ServiceBuilder()
                    .withContext("http://iiif.io/api/annex/service/physdim/1/context.json")
                    .withProfile("http://iiif.io/api/annex/service/physdim")
                    .withPhysicalScale(getphysicalScale(width, scanResolution))
                    .withPhysicalUnits("in")
                    .build();
            
            service = new ServiceBuilder()
                .withContext("http://iiif.io/api/image/2/context.json")
                .withProtocol("http://iiif.io/api/image")
                .withIdentifier(id)
                .withProfile("http://iiif.io/api/image/2/level1.json")
                .withWidth(width)
                .withHeight(height)
                .withService(physicalService)
                .build();
        }
        return new Resource(id, type, format, width, height, description, service);
    }
    
    private double getphysicalScale(int width, int scanResolution) {
        return ((double) width / (double) scanResolution) / (double) width;
    }

}
