package no.nb.microservices.iiifpresentation.rest.controller;

import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.IiifPresentationContext;
import no.nb.microservices.iiifpresentation.model.NullContext;

public class ContextFactory {

    private ContextFactory() {
        super();
    }
    
    public static Context getInstance(String acceptType) {
        Context context = null;
        if("application/ld+json".equalsIgnoreCase(acceptType)) {
                context = new NullContext();
        } else {
                context = new IiifPresentationContext();       
        }
        return context;
    }
}
