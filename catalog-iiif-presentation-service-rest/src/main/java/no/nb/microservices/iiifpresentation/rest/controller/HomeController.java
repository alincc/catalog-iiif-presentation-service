package no.nb.microservices.iiifpresentation.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.service.ManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController("/iiif")
@Api(value = "/iiif", description = "Home api")
public class HomeController {

    private final ManifestService manifestService;

    @Autowired
    public HomeController(ManifestService manifestService) {
        this.manifestService = manifestService;
    }

    @ApiOperation(value = "Hello World", notes = "Hello World notes", response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response") })
    @RequestMapping(value = "/{itemId}/manifest", method = RequestMethod.GET)
    public Manifest getManifest(@PathVariable String itemId) throws ExecutionException, InterruptedException {
        return manifestService.getManifest(itemId);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "It looks like we have a internal error in our application. The error have been logged and will be looked at by our development team")
    public void defaultHandler() {
        // TODO: Log error and notify
    }
}
