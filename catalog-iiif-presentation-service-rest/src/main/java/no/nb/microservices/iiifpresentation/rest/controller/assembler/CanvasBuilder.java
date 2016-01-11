package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.ContentResource;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class CanvasBuilder {

    private Context context;
    private String manifestId;
    private Div div;
    
    public CanvasBuilder() {
        context = new NullContext();
    }
    
    public CanvasBuilder withContext(Context context) {
        this.context = context;
        return this;
    }
    
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

        Link selfRel = linkTo(methodOn(ManifestController.class).getCanvas(manifestId, div.getId(), null)).withSelfRel();
        List<Annotation> images = createImages();
        
        List<Object> hotspots = createHotspots();
        return new Canvas(context, selfRel.getHref(), div.getType(), div.getResource().getWidth(), div.getResource().getHeight(), images, hotspots);
    }

    private List<Object> createHotspots() {
        if (div.getHotspots() == null || div.getHotspots().isEmpty()) {
            return Collections.emptyList();
        }
        
        ContentResource contentResource = new ContentResource();
        Link hotspotRel = linkTo(methodOn(ManifestController.class).getHotspots(manifestId, div.getId(), null)).withSelfRel();
        contentResource.setId(hotspotRel.getHref());
        contentResource.setType("sc:AnnotationList");
        
        return Arrays.asList(contentResource);
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
        String canvasId = div.getId();
        Link canvasRel = linkTo(methodOn(ManifestController.class).getCanvas(manifestId, canvasId, null)).withSelfRel();

        no.nb.microservices.catalogmetadata.model.struct.Resource resource = div.getResource();
        String id = new IiifImageServerUrlBuilder()
                .withIdentifer(resource.getHref())
                .toString();

        Resource iiifResource = new ResourceBuilder()
                .withId(id)
                .withType("dctypes:Image")
                .withFormat("image/jpeg")
                .withWidth(resource.getWidth())
                .withHeight(resource.getHeight())
                .withScanResolution(resource.getScanResolution())
                .build();
        
        Link selfRel = linkTo(methodOn(ManifestController.class).getAnnotation(manifestId, div.getId(), null)).withSelfRel();
        Annotation annotation = new AnnotationBuilder()
                .withId(selfRel.getHref())
                .withMotivation("sc:painting")
                .withResource(iiifResource)
                .withOn(canvasRel.getHref())
                .build();
        return Arrays.asList(annotation);
    }
}

