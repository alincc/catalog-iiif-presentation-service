package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Service;

public class ServiceBuilder {

    private String identifier;
    private int width;
    private int height;
    private double physicalScale;
    
    public ServiceBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }
    
    public ServiceBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public ServiceBuilder withHeight(int height) {
        this.height = height;
        return this;
    }

    public ServiceBuilder withPhysicalScale(int scanResolution) {
        this.physicalScale = getphysicalScale(width, scanResolution);
        return this;
    }

    private double getphysicalScale(int width, int scanResolution) {
        return physicalScale = ((double) width / (double) scanResolution) / (double) width;
    }

    public Service build() {
        String id = new IiifImageServerUrlBuilder()
                .withIdentifer(identifier).toString();
        return new Service(id, width, height, physicalScale);
    }

}
