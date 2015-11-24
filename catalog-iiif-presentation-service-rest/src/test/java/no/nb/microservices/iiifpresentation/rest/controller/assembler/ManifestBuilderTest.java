package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.model.Manifest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.*;

public class ManifestBuilderTest {

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/catalog/iiif/id1/manifest");
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
        StructMap struct = TestStructMap.aDefaultStructMap().build();

        Manifest manifest = new ManifestBuilder(id).withItem(item).withStruct(struct).build();

        assertNotNull("Manifest should not be null", manifest);
        assertEquals("Should have a id", "http://localhost/v1/catalog/iiif/"+id+"/manifest", manifest.getId());
        assertTrue("Should have a description", !manifest.getDescription().isEmpty());
        assertTrue("Should have a sequence", manifest.getSequences().size() == 1);
    }

}
