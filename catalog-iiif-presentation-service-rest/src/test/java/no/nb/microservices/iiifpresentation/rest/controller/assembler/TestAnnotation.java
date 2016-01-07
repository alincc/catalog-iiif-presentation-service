package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Resource;

public final class TestAnnotation {

    public static AnnotationBuilder aMinimalAnnotation() {
        Resource resource = new ResourceBuilder()
                .withId("http://www.example.org/page-to-go-to.html")
                .build();
        return new AnnotationBuilder()
                .withId("http://www.example.org/iiif/book1/annotation/anno1")
                .withResource(resource);
    }
}
