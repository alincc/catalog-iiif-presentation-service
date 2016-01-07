package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.hateoas.Link;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.Hotspot;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.exception.AnnotationNotFoundException;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.AnnotationList;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Resource;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class AnnotationListBuilder {
    private Context context;
    private String manifestId;
    private String name;
    private StructMap struct;

    public AnnotationListBuilder() {
        super();
        context = new NullContext();
    }
    
    public AnnotationListBuilder withContext(Context context) {
        this.context = context;
        return this;
    }
    
    public AnnotationListBuilder withManifestId(final String manifestId) {
        this.manifestId = manifestId;
        return this;
    }

    public AnnotationListBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public AnnotationListBuilder withStruct(StructMap struct) {
        this.struct = struct;
        return this;
    }
    
    public AnnotationList build() {
        AnnotationList annotationList = new AnnotationList();
        annotationList.setContext(context);

        Link selfRel = linkTo(methodOn(ManifestController.class).getHotspots(manifestId, name, null)).withSelfRel();
        annotationList.setId(selfRel.getHref());
        Div div = struct.getDivById(name);
        if (div == null) {
            throw new AnnotationNotFoundException(name + " not found"); 
        }
        for(Hotspot hotspot : div.getHotspots()) {
            String on = createOnLink(manifestId, div.getId(), hotspot.getL(), hotspot.getT(), hotspot.getWidth(), hotspot.getHeight()).getHref(); 
            Resource resource = new ResourceBuilder()
                    .withId(hotspot.getHs().getHsId())
                    .withType("dctypes:Text")
                    .withFormat("text/html")
                    .withDescription(StringEscapeUtils.escapeHtml4(hotspot.getHs().getValue()))
                    .build();
            Link annoRel = linkTo(methodOn(ManifestController.class).getHotspot(manifestId, name, hotspot.getHszId(), null)).withSelfRel();
            Annotation annotation = new AnnotationBuilder()
                .withContext(new NullContext())
                .withId(annoRel.getHref())
                .withMotivation("oa:linking")
                .withOn(on)
                .withResource(resource)
                .build();

            annotationList.addResource(annotation);
        }
        return annotationList;
    }

    private Link createOnLink(String manifestId, String canvasId, int x, int y, int w, int h) {
        return ResourceLinkBuilder.linkTo(ResourceTemplateLink.PRESENTATION, manifestId, canvasId, x, y, w, h).withRel("on");
    }    

}
