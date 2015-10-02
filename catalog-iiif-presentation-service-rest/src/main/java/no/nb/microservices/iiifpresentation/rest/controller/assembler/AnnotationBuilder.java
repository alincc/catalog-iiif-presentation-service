package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class AnnotationBuilder {

    private Context context;
    private String manifestId;
    private String canvasId;
    private no.nb.microservices.catalogmetadata.model.struct.Resource resource; 
    
    public AnnotationBuilder() {
        super();
        context = new NullContext();
    }
    
    public AnnotationBuilder withContext(Context context) {
        this.context = context;
        return this;
    }

    public AnnotationBuilder withManifestId(String manifestId) {
        this.manifestId = manifestId;
        return this;
    }
    
    public AnnotationBuilder withCanvasId(String canvasId) {
        this.canvasId = canvasId;
        return this;
    }

    public AnnotationBuilder withResource(no.nb.microservices.catalogmetadata.model.struct.Resource resource) {
        this.resource = resource;
        return this;
    }

    public Annotation build() {
        validate();
        
        Link selfRel = linkTo(methodOn(ManifestController.class).getAnnotation(manifestId, resource.getHref())).withSelfRel();
        Link canvasRel = linkTo(methodOn(ManifestController.class).getCanvas(manifestId, canvasId)).withSelfRel();
        
        Resource iiifResource = new ResourceBuilder()
                .withImageId(resource.getHref())
                .withWidth(resource.getWidth())
                .widthHeight(resource.getHeight())
                .build();
        return new Annotation(context, selfRel.getHref(), canvasRel.getHref(), iiifResource);
    }

    private void validate() {
        if (manifestId == null || manifestId.isEmpty()) {
            throw new IllegalStateException("Missing required manifestId");
        }
        if (canvasId == null || canvasId.isEmpty()) {
            throw new IllegalStateException("Missing required canvasId");
        }
        if (resource == null || resource.getHref() == null) {
            throw new IllegalStateException("Missing required resource href");
        }
    }
    
}
