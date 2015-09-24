package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.microservices.iiifpresentation.model.Canvas;

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
        
        assertEquals("http://localhost/catalog/iiif/id1/canvas/id1", canvas.getId());
    }

    @Test(expected=IllegalStateException.class)
    public void whenMissingIdThenThrowException() {
        new CanvasBuilder().build();
    }
    
    @Test(expected=IllegalStateException.class)
    public void whenMissingLabelThenThrowException() {
        new CanvasBuilder()
            .withId("id1")
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

    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/iiif/id1/canvas/p1");
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
