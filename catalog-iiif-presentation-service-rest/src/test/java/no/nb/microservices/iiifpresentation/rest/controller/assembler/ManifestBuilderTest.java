package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.iiifpresentation.model.Manifest;

public class ManifestBuilderTest {

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/iiif/id1/manifest");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }
    
    
    @Test
    public void testBuild() {
        String id = "id1";
        ItemResource item = new ItemResource();
        Metadata metadata = new Metadata();
        metadata.setSummary("A short summary");
        item.setMetadata(metadata);
        Manifest manifest = new ManifestBuilder(id).withItem(item).build();

        assertNotNull("Manifest should not be null", manifest);
        assertEquals("Should have a id", "http://localhost/catalog/iiif/"+id+"/manifest", manifest.getId());
        assertTrue("Shuld have adescription", !manifest.getDescription().isEmpty());
    }

}
