package no.nb.microservices.iiifpresentation.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import no.nb.htrace.annotation.Traceable;
import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.core.manifest.ManifestService;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.IiifPresentationContext;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.model.Sequence;
import no.nb.microservices.iiifpresentation.rest.controller.assembler.AnnotationBuilder;
import no.nb.microservices.iiifpresentation.rest.controller.assembler.CanvasBuilder;
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
    @RequestMapping(value = "/{manifestId}/manifest", method = RequestMethod.GET)
    public ResponseEntity<Manifest> getManifest(@PathVariable String manifestId,
            @RequestHeader(value="Accept", defaultValue=MediaType.APPLICATION_JSON_VALUE) String acceptType) {
        ItemStructPair itemStructPair = manifestService.getManifest(manifestId);
        
        Manifest manifest = new ManifestBuilder(manifestId)
                .withContext(new IiifPresentationContext())
                .withItem(itemStructPair.getItem())
                .withStruct(itemStructPair.getStruct())
                .build();

        return new ResponseEntity<>(manifest, createIiifHeaders(acceptType), HttpStatus.OK);
    }

    @Traceable(description="sequence")
    @RequestMapping(value = "/{manifestId}/sequence/normal", method = RequestMethod.GET)
    public ResponseEntity<Sequence> getSequence(@PathVariable String manifestId,
            @RequestHeader(value="Accept", defaultValue=MediaType.APPLICATION_JSON_VALUE) String acceptType) {
        ItemStructPair itemStructPair = manifestService.getManifest(manifestId);
        
        Sequence sequence = new SequenceBuilder()
                .withContext(new IiifPresentationContext())
                .withManifestId(manifestId)
                .withStruct(itemStructPair.getStruct())
                .build();

        return new ResponseEntity<>(sequence, createIiifHeaders(acceptType), HttpStatus.OK);
    }

    @Traceable(description="canvas")
    @RequestMapping(value = "/{manifestId}/canvas/{name}", method = RequestMethod.GET)
    public ResponseEntity<Canvas> getCanvas(@PathVariable String manifestId,
            @PathVariable String name,
            @RequestHeader(value="Accept", defaultValue=MediaType.APPLICATION_JSON_VALUE) String acceptType) {
        ItemStructPair itemStructPair = manifestService.getManifest(manifestId);
        
        Div div = itemStructPair.getStruct().getDivById(name);
        
        Canvas canvas = new CanvasBuilder()
                .withContext(new IiifPresentationContext())
                .withManifestId(manifestId)
                .withDiv(div)
                .build();

        return new ResponseEntity<>(canvas, createIiifHeaders(acceptType), HttpStatus.OK);
    }

    @Traceable(description="annotation")
    @RequestMapping(value = "/{manifestId}/annotation/{name}", method = RequestMethod.GET)
    public ResponseEntity<Annotation> getAnnotation(@PathVariable  String manifestId, 
            @PathVariable  String name,
            @RequestHeader(value="Accept", defaultValue=MediaType.APPLICATION_JSON_VALUE) String acceptType) {
        ItemStructPair itemStructPair = manifestService.getManifest(manifestId);

        Div div = itemStructPair.getStruct().getDivByHref(name);

        Annotation annotation = new AnnotationBuilder()
                .withContext(new IiifPresentationContext())
                .withManifestId(manifestId)
                .withCanvasId(div.getId())
                .withResource(div.getResource())
                .build();

        return new ResponseEntity<>(annotation, createIiifHeaders(acceptType), HttpStatus.OK);
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

    private HttpHeaders createIiifHeaders(String acceptType) {
        Context context = ContextFactory.getInstance(acceptType);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(context.getHeaders());
        return headers;
    }    
}
