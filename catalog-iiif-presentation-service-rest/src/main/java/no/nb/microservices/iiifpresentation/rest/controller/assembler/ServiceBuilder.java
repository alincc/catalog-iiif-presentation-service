package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import java.util.Arrays;

import no.nb.microservices.iiifpresentation.model.Service;

public class ServiceBuilder {

    private String identifier;
    private Integer width;
    private Integer height;
    private String context;
    private String profile;
    private String protocol;
    private Double physicalScale;
    private String physicalUnits;
    private Service service;
    
    public ServiceBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }
    
    public ServiceBuilder withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public ServiceBuilder withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public ServiceBuilder withPhysicalScale(Double physicalScale) {
        this.physicalScale = physicalScale;
        return this;
    }
    
    public ServiceBuilder withPhysicalUnits(String physicalUnits) {
        this.physicalUnits = physicalUnits;
        return this;
    }

    public ServiceBuilder withContext(String context) {
        this.context = context;
        return this;
    }
    
    public ServiceBuilder withProfile(String profile) {
        this.profile = profile;
        return this;
    }
    
    public ServiceBuilder withProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public ServiceBuilder withService(Service service) {
        this.service = service;
        return this;
    }

    public Service build() {
        String id = null;
        if (identifier != null) {
            id = new IiifImageServerUrlBuilder()
                    .withIdentifer(identifier).toString();
        }
        
        return new Service(context, Arrays.asList(profile), protocol, id, width, height, physicalScale, physicalUnits, service);
    }

}
