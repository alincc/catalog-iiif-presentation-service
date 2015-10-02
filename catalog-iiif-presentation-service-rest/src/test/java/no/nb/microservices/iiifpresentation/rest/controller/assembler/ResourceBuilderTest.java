package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.*;

import org.junit.Test;

import no.nb.microservices.iiifpresentation.model.Resource;

public class ResourceBuilderTest {

    @Test
    public void testBuild() {
        int width = 10;
        int height = 20;
        Resource resource = new ResourceBuilder()
            .withImageId("id1")
            .withWidth(width)
            .widthHeight(height)
            .build();
        
        assertEquals("should have a id pointing to image", "http://www.nb.no/services/image/resolver/id1", resource.getId());
        assertEquals("Should have a type", "dctypes:Image", resource.getType());
        assertEquals("Should have a format", "image/jpeg", resource.getFormat());
        assertEquals("should have widht", width, resource.getWidth());
        assertEquals("should have height", height, resource.getHeight());
        assertNotNull("Should hava a service", resource.getService());
    }

}
