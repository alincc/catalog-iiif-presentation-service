package no.nb.microservices.iiifpresentation.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import no.nb.microservices.iiifpresentation.model.Manifest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/iiif", description = "Home api")
public class HomeController {

    @ApiOperation(value = "Hello World", notes = "Hello World notes", response = String.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response") })
    @RequestMapping(value = "/{itemId}/manifest", method = RequestMethod.GET)
    public Manifest getManifest() {
        return new Manifest();
    }
}
