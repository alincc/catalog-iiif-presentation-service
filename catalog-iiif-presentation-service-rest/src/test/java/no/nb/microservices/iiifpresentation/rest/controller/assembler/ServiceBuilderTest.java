package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import no.nb.microservices.iiifpresentation.model.Service;
import no.nb.microservices.iiifpresentation.model.Size;

public class ServiceBuilderTest {

    @Test
    public void testContext() {
        Service service = TestService.aDefaultService()
                .withContext("http://iiif.io/api/image/1/context.json")
                .build();
        
        assertEquals("http://iiif.io/api/image/1/context.json", service.getContext());
    }

    @Test
    public void testId() {
        Service service = TestService
                .aDefaultService()
                .withIdentifier(TestService.DEFAULT_IDENTIFIER)
                .build();
        
        assertEquals("http://www.nb.no/services/image/resolver/" + TestService.DEFAULT_IDENTIFIER, service.getId());
    }

    @Test
    public void testProtocol() {
        Service service = TestService.aDefaultService()
                .withProtocol("http://iiif.io/api/image")
                .build();
        
        assertEquals("http://iiif.io/api/image", service.getProtocol());
    }

    @Test
    public void testWidth() {
        Service service = TestService
                .aDefaultService()
                .withWidth(10)
                .build();
        
        assertEquals(10, service.getWidth().intValue());
    }

    @Test
    public void testHeight() {
        Service service = TestService
                .aDefaultService()
                .withHeight(10)
                .build();
        
        assertEquals(10, service.getHeight().intValue());
    }

    @Test
    public void testPhysicalScale() {
        Service service = TestService
                .aDefaultService()
                .withPhysicalScale(0.0025)
                .build();

        assertEquals(0.0025, service.getPhysicalScale(), 0.0001f);
    }

    @Test
    public void testTiles() {
        Service service = TestService
                .aDefaultService()
                .withWidth(6000)
                .withHeight(4000)
                .build();
        
        List<Integer> scaleFactors = Arrays.asList(new Integer(1),new Integer(2),new Integer(4),new Integer(8),new Integer(16), new Integer(32));
        assertEquals("Width should be 1024", 1024, service.getTiles().get(0).getWidth().intValue());
        assertEquals("ScaleFactors should be 1,2,4,8,16", scaleFactors, service.getTiles().get(0).getScaleFactors());
    }

    @Test
    public void testProfile() {
        Service service = TestService
                .aDefaultService()
                .withProfile("http://library.stanford.edu/iiif/image-api/1.1/compliance.html#level0")
                .build();
        
        assertEquals("http://library.stanford.edu/iiif/image-api/1.1/compliance.html#level0", service.getProfile().get(0));
    }
    
    @Test
    public void testSizes() {
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size(3000, 2000));
        sizes.add(new Size(1500, 1000));
        sizes.add(new Size(750, 500));
        sizes.add(new Size(375, 250));
        sizes.add(new Size(187, 125));
        
        Service service = TestService
                .aDefaultService()
                .withWidth(6000)
                .withHeight(4000)
                .build();
        
        assertTrue(sizes.equals(service.getSizes()));
    }

}
