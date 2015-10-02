package no.nb.microservices.iiifpresentation.rest.controller;

import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.IiifPresentationContext;
import no.nb.microservices.iiifpresentation.model.NullContext;

public class ContextFactory {

    public static Context getInstance(String acceptType) {
        Context context = null;
        switch(acceptType) {
            case "application/ld+json":
                context = new NullContext();
                break;
            default:
                context = new IiifPresentationContext();       
        }
        return context;
    }
}
