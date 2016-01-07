package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Resource;

public class AnnotationBuilder {

    private Context context;
    private String id;
    private String motivation;
    private Resource resource;
    private String on;
    
    public AnnotationBuilder() {
        super();
        context = new NullContext();
    }
    
    public AnnotationBuilder withContext(Context context) {
        this.context = context;
        return this;
    }
    
    public AnnotationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AnnotationBuilder withMotivation(String motivation) {
        this.motivation = motivation;
        return this;
    }

    public AnnotationBuilder withResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public AnnotationBuilder withOn(String on) {
        this.on = on;
        return this;
    }
    
    public Annotation build() {
        validate();
        
        return new Annotation(context, id, motivation, on, resource);
    }

    private void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("Missing required id");
        }
    }

}
