package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.model.struct.Resource;

public final class TestAnnotation {

    public static AnnotationBuilder aMinimalAnnotation() {
        Resource resource = new Resource();
        resource.setHref("h1");
        return new AnnotationBuilder()
                .withManifestId("m1")
                .withCanvasId("c1")
                .withResource(resource);
    }
}
