package no.nb.microservices.iiifpresentation.exception;

public class HotspotNotFoundException  extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HotspotNotFoundException(String message) {
        super(message);
    }
    
    public HotspotNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
}
