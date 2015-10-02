package no.nb.microservices.iiifpresentation.rest.controller.assembler;

public final class TestService {
    public final static String DEFAULT_IDENTIFIER = "abcd1234"; 
    
    public static ServiceBuilder aDefaultService() {
        return new ServiceBuilder()
                .withIdentifier(DEFAULT_IDENTIFIER)
                .withWidth(6000)
                .withHeight(4000);
    }

}
