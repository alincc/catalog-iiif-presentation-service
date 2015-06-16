package no.nb.microservices.iiifpresentation.rest.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.service.IManifestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    private IManifestService manifestService;
    
    private HomeController homeController;
    
    @Before
    public void setup() {
        homeController = new HomeController(manifestService);
    }
    
    @Test
    public void testGetManifest() {
        final String itemId = "12345";
        final HttpServletRequest request = new MockHttpServletRequest("GET", "/iiif/" + itemId + "/manifest");
        
        when(manifestService.getManifest(itemId, "http://localhost/iiif/" + itemId + "/manifest")).thenReturn(new Manifest());
        assertNotNull("Should return a manifest", homeController.getManifest(itemId, request));
    }
    
    @Test
    public void testDefaultHandler() {
        final String itemId = "12345";
        final HttpServletRequest request = new MockHttpServletRequest("GET", "/iiif/" + itemId + "/manifest");
        homeController.defaultHandler(request, new Exception("junit test exception"));
    }
    
}
