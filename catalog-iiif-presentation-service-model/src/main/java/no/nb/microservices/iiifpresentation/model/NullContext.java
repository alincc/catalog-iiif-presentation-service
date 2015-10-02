package no.nb.microservices.iiifpresentation.model;

public class NullContext implements Context {

    @Override
    public String getContext() {
        return null;
    }

}
