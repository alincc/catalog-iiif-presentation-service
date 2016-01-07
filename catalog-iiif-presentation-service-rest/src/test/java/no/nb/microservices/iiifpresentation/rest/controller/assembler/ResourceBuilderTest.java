package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.*;

import org.junit.Test;

import no.nb.microservices.iiifpresentation.model.Resource;

public class ResourceBuilderTest {

    @Test
    public void testBuild() {
        int width = 10;
        int height = 20;
        int scanResolution = 400;
        double physicalScale = 0.0025;
        Resource resource = new ResourceBuilder()
            .withId("http://www.nb.no/services/image/resolver/id1")
            .withType("dctypes:Image")
            .withFormat("image/jpeg")
            .withWidth(width)
            .withHeight(height)
            .withScanResolution(scanResolution)
            .build();
        assertEquals("should have a id pointing to image", "http://www.nb.no/services/image/resolver/id1", resource.getId());
        assertEquals("Should have a type", "dctypes:Image", resource.getType());
        assertEquals("Should have a format", "image/jpeg", resource.getFormat());
        assertEquals("should have widht", width, resource.getWidth().intValue());
        assertEquals("should have height", height, resource.getHeight().intValue());
        assertEquals(physicalScale, resource.getService().getService().getPhysicalScale(), 0.0001f);
        assertNotNull("Should hava a service", resource.getService());
    }

}
