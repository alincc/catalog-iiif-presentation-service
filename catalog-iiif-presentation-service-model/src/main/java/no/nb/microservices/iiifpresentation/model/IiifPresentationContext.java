package no.nb.microservices.iiifpresentation.model;

public class IiifPresentationContext implements Context {

    @Override
    public String getContext() {
        return "http://iiif.io/api/presentation/2/context.json";
    }

}
