package no.nb.microservices.iiifpresentation.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import no.nb.htrace.annotation.Traceable;
import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.core.manifest.ManifestService;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.model.Sequence;
import no.nb.microservices.iiifpresentation.rest.controller.assembler.ManifestBuilder;
import no.nb.microservices.iiifpresentation.rest.controller.assembler.SequenceBuilder;

@RestController
@RequestMapping("/catalog/iiif")
@Api(value = "/iiif", description = "Home api")
public class ManifestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestController.class);

    private final ManifestService manifestService;

    @Autowired
    public ManifestController(ManifestService manifestService) {
        this.manifestService = manifestService;
    }

    @ApiOperation(value = "Hello World", notes = "Hello World notes", response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response") })
    @Traceable(description="manifest")
    @RequestMapping(value = "/{id}/manifest", method = RequestMethod.GET)
    public ResponseEntity<Manifest> getManifest(@PathVariable String id) {
        ItemStructPair itemStructPair = manifestService.getManifest(id);
        Manifest manifest = new ManifestBuilder(id)
                .withItem(itemStructPair.getItem())
                .withStruct(itemStructPair.getStruct())
                .build();

        return new ResponseEntity<>(manifest, HttpStatus.OK);
    }

    @Traceable(description="sequence")
    @RequestMapping(value = "/{id}/sequence/normal", method = RequestMethod.GET)
    public ResponseEntity<Sequence> getSequence(@PathVariable String id) {
        ItemStructPair itemStructPair = manifestService.getManifest(id);
        
        Sequence sequence = new SequenceBuilder()
                .withId(id)
                .withStruct(itemStructPair.getStruct())
                .build();

        return new ResponseEntity<>(sequence, HttpStatus.OK);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "It looks like we have a internal error in our application. The error have been logged and will be looked at by our development team")
    public void defaultHandler(HttpServletRequest req, Exception e) {

        // Build Header string
        StringBuilder headers = new StringBuilder();
        for (String headerKey : Collections.list(req.getHeaderNames())) {
            String headerValue = req.getHeader(headerKey);
            headers.append(headerKey + ": " + headerValue + ", ");
        }

        LOGGER.error("" +
                "Got an unexcepted exception.\n" +
                "Context Path: " + req.getContextPath() + "\n" +
                "Request URI: " + req.getRequestURI() + "\n" +
                "Query String: " + req.getQueryString() + "\n" +
                "Method: " + req.getMethod() + "\n" +
                "Headers: " + headers + "\n" +
                "Auth Type: " + req.getAuthType() + "\n" +
                "Remote User: " + req.getRemoteUser() + "\n" +
                "Username: " + ((req.getUserPrincipal()  != null) ? req.getUserPrincipal().getName() : "Anonymous") + "\n"
                , e);
    }
}