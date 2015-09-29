package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class CanvasBuilder {

    private String manifestId;
    private Div div;
    
    public CanvasBuilder withManifestId(String manifestId) {
        this.manifestId = manifestId;
        return this;
    }

    public CanvasBuilder withDiv(final Div div) {
        this.div = div;
        return this;
    }
    
    public Canvas build() {
        validate();

        Link selfRel = linkTo(methodOn(ManifestController.class).getCanvas(manifestId, div.getId())).withSelfRel();
        List<Annotation> images = createImages();
        
        return new Canvas(selfRel.getHref(), div.getType(), div.getResource().getWidth(), div.getResource().getHeight(), images);
    }

    private void validate() {
        if (manifestId == null || manifestId.isEmpty()) {
            throw new IllegalStateException("Missing required manifestId");
        }
        if (div == null) {
            throw new IllegalStateException("Missing required div");
        }

    }

    private List<Annotation> createImages() {
        Annotation annotation = new AnnotationBuilder()
                .withManifestId(manifestId)
                .withCanvasId(div.getId())
                .withResource(div.getResource())
                .build();
        List<Annotation> images = Arrays.asList(annotation);
        return images;
    }
}

