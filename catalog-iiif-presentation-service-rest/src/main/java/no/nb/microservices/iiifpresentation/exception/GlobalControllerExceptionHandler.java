package no.nb.microservices.iiifpresentation.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(ItemNotFoundException.class)
    public void handleItemNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No such Item");
    }

    @ExceptionHandler(CanvasNotFoundException.class)
    public void handleCanvasNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No such Canvas");
    }

    @ExceptionHandler(AnnotationNotFoundException.class)
    public void handleAnnotationNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No such Annotation");
    }
    
    @ExceptionHandler(HotspotNotFoundException.class)
    public void handleHotspotNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No such hotspot");
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "It looks like we have a internal error in our application. The error have been logged and will be looked at by our development team")
    public void defaultHandler(HttpServletRequest req, Exception e) {
        LOGGER.error("" +
                "Got an unexcepted exception.\n" +
                "Request URI: " + req.getRequestURI() + "\n" +
                "Auth Type: " + req.getAuthType() + "\n" +
                "Context Path: " + req.getContextPath() + "\n" +
                "Path Info: " + req.getPathInfo() + "\n" +
                "Query String: " + req.getQueryString() + "\n" +
                "Remote User: " + req.getRemoteUser() + "\n" +
                "Method: " + req.getMethod() + "\n" +
                "Username: " + ((req.getUserPrincipal()  != null) ? req.getUserPrincipal().getName() : "Anonymous") + "\n"
                , e);
    }

}
