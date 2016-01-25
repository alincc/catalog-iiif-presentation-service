package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Sequence;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class SequenceBuilder {

    private Context context;
    private String manifestId;
    private StructMap struct;
    
    public SequenceBuilder() {
        super();
        context = new NullContext();
    }

    public SequenceBuilder withContext(final Context context) {
        this.context = context;
        return this;
    }
    
    public SequenceBuilder withManifestId(String manifestId) {
        this.manifestId = manifestId;
        return this;
    }

    public SequenceBuilder withStruct(final StructMap struct) {
        this.struct = struct;
        return this;
    }
    
    public Sequence build() {
        validate();
        Link selfRel = linkTo(methodOn(ManifestController.class).getSequence(manifestId, null)).withSelfRel();
        Sequence sequence = new Sequence(context, selfRel.getHref());

        for(Div div : struct.getDivs()) {
            Canvas canvas = new CanvasBuilder()
                .withManifestId(manifestId)
                .withDiv(div)
                .build();
            sequence.addCanvas(canvas);
        }
        
        return sequence;
    }

    private void validate() {
        if (manifestId == null || manifestId.isEmpty()) {
            throw new IllegalStateException("Missing manifestId");
        }
        if (struct == null || struct.getDivs() == null || struct.getDivs().isEmpty()) {
            throw new IllegalStateException("Missing struct");
        }
    }

}
