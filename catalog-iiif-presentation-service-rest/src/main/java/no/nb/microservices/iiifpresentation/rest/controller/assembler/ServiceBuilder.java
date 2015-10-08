package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Service;

public class ServiceBuilder {

    private String identifier;
    private int width;
    private int height;
    
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

    public Service build() {
        String id = new IiifImageServerUrlBuilder()
                .withIdentifer(identifier).toString();
        return new Service(id, width, height);
    }

}
