package no.nb.microservices.iiifpresentation.exception;

public class ItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String message) {
        super(message);
    }
    
    public ItemNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
}
