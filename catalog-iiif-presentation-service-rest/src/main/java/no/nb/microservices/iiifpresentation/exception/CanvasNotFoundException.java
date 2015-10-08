package no.nb.microservices.iiifpresentation.exception;

public class CanvasNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CanvasNotFoundException(String message) {
        super(message);
    }
    
    public CanvasNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
}
