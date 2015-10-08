package no.nb.microservices.iiifpresentation.exception;

public class AnnotationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AnnotationNotFoundException(String message) {
        super(message);
    }
    
    public AnnotationNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
}
