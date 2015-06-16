package no.nb.microservices.iiifpresentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Can't retrive item")
public class RetrieveItemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RetrieveItemException(String message) {
        super(message);
    }
}
