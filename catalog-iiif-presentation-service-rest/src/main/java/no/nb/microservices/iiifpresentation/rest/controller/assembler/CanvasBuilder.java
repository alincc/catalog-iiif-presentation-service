package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class CanvasBuilder {

    private String id;
    private String name;
    private String pageUrn;
    private String label;
    private int width;
    private int height;
    
    public CanvasBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public CanvasBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CanvasBuilder withPageUrn(String pageUrn) {
        this.pageUrn = pageUrn;
        return this;
    }

    public CanvasBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public CanvasBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public CanvasBuilder withHeight(int height) {
        this.height = height;
        return this;
    }

    public Canvas build() {
        validate();

        Link selfRel = linkTo(methodOn(ManifestController.class).getCanvas(id, name)).withSelfRel();
        return new Canvas(selfRel.getHref(), label, width, height);
    }

    private void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("Missing required id");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Missing required name");
        }

        if (label == null || label.isEmpty()) {
            throw new IllegalStateException("Missing required label");
        }
    }

}
