package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Canvas;

public class CanvasFixtures {
    public final Canvas CANVAS = new CanvasBuilder()
            .withId("id1")
            .withName("p1")
            .withLabel("label1")
            .withWidth(1)
            .withHeight(2)
            .build();

}
