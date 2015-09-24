package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.Sequence;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class SequenceBuilder {

    private String id;
    private StructMap struct;
    
    public SequenceBuilder() {
        super();
    }

    public SequenceBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public SequenceBuilder withStruct(final StructMap struct) {
        this.struct = struct;
        return this;
    }

    public Sequence build() {
        validate();
        Link selfRel = linkTo(methodOn(ManifestController.class).getSequence(id)).withSelfRel();
        Sequence sequence = new Sequence(selfRel.getHref());
        
        for(Div div : struct.getDivs()) {
            Canvas canvas = new CanvasBuilder()
                .withId(id)
                .withDiv(div)
                .build();
            sequence.addCanvas(canvas);
        }
        
        return sequence;
    }

    private void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("Missing id");
        }
        if (struct == null || struct.getDivs().isEmpty()) {
            throw new IllegalStateException("Missing struct");
        }
    }

}
