package no.nb.microservices.iiifpresentation.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import no.nb.microservices.iiifpresentation.Application;
import no.nb.microservices.iiifpresentation.model.Manifest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port: 0")
public class HomeControllerTest {

    @Value("${local.server.port}")
    int port;
    
    RestTemplate restTemplate = new TestRestTemplate();
    
    @Test
    public void testGetManifest() {
        final String identifier = "78234";
        final String id = "http://localhost:" + port + "/iiif/" + identifier + "/manifest";
        ResponseEntity<Manifest> result = restTemplate.getForEntity(id, Manifest.class);
        
        assertTrue("Status code should be 2xx", result.getStatusCode().is2xxSuccessful());
        assertEquals(id, result.getBody().getId());
    }
}
