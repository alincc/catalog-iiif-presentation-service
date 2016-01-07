package no.nb.microservices.iiifpresentation.rest.controller.assembler;

public enum ResourceTemplateLink {

    PRESENTATION ("/v1/catalog/iiif/{id}/canvas/{name}#{x},{y},{w},{h}");
    private final String resourceLink;

    ResourceTemplateLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public String getTemplate() {
        return resourceLink;
    }

}
