package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class CanvasBuilder {

    private String id;
    private Div div;
    
    public CanvasBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public CanvasBuilder withDiv(final Div div) {
        this.div = div;
        return this;
    }
    
    public Canvas build() {
        validate();

        Link selfRel = linkTo(methodOn(ManifestController.class).getCanvas(id, div.getId())).withSelfRel();
        return new Canvas(selfRel.getHref(), div.getType(), div.getResource().getWidth(), div.getResource().getHeight());
    }

    private void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("Missing required id");
        }
        if (div == null) {
            throw new IllegalStateException("Missing required div");
        }

    }

}
