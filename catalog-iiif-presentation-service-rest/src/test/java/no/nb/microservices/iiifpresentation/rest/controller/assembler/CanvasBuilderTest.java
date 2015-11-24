package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Canvas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CanvasBuilderTest {

    @Before
    public void init() {
        createDefaultRequestAttributes();
    }

    @After
    public void cleanUp() {
        resetRequestAttributes();
    }

    @Test
    public void canvasMustHaveId() {
        Canvas canvas = createDefaultCanvas();
        
        assertEquals("http://localhost/v1/catalog/iiif/id1/canvas/id1", canvas.getId());
    }

    @Test(expected=IllegalStateException.class)
    public void whenMissingIdThenThrowException() {
        new CanvasBuilder().build();
    }
    
    @Test(expected=IllegalStateException.class)
    public void whenMissingLabelThenThrowException() {
        new CanvasBuilder()
            .withManifestId("id1")
            .build();
    }

    @Test
    public void canvasMustHaveLabel() {
        Canvas canvas = createDefaultCanvas();
        
        assertEquals("PAGE", canvas.getLabel());
    }

    @Test
    public void canvasMustHaveWidth() {
        Canvas canvas = createDefaultCanvas();
        
        assertEquals(1, canvas.getWidth());
    }

    @Test
    public void canvasMustHaveHeight() {
        Canvas canvas = createDefaultCanvas();
        
        assertEquals(2, canvas.getHeight());
    }

    @Test
    public void canvasMustHaveImages() {
        Canvas canvas = createDefaultCanvas();
        
        assertNotNull(canvas.getImages());
    }
    
    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/catalog/iiif/id1/canvas/p1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
    
    private void resetRequestAttributes() {
        RequestContextHolder.resetRequestAttributes();
    }

    private Canvas createDefaultCanvas() {
        return TestCanvas.aDefaultCanvas().build();
    }
    
}
