package no.nb.microservices.iiifpresentation.reactor;

/**
 * Created by andreasb on 11.06.15.
 */
public class LatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LatchException(Exception ex) {
        super(ex);
    }


}

