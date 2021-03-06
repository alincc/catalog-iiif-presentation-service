package no.nb.microservices.iiifpresentation.rest.controller.assembler;

public class IiifImageServerUrlBuilder {

    public static final String IIIF_IMAGE_SERVER_TEMPLATE = "http://www.nb.no/services/image/resolver/{identifier}";
    
    private String identifier;
    
    public IiifImageServerUrlBuilder withIdentifer(String identifier) {
        this.identifier = identifier;
        return this;
    }
    
    @Override
    public String toString() {
        return IIIF_IMAGE_SERVER_TEMPLATE.replace("{identifier}", identifier);
    }
    
}
