package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.ContentResource;

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
        
        assertEquals("http://localhost/catalog/v1/iiif/id1/canvas/URN:NBN:no-nb_digibok_2001010100001_123", canvas.getId());
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
    
    @Test
    public void canvasCanHaveLinksToHotspots() {
        Canvas canvas = TestCanvas.aCanvasWithHotspots().build();
        
        assertThat(canvas.getOtherContent().size(), is(1));
        ContentResource resource = (ContentResource)canvas.getOtherContent().get(0);
        assertThat(resource.getId(), is("http://localhost/catalog/v1/iiif/id1/hotspots/id1"));
        assertThat(resource.getType(), is("sc:AnnotationList"));
    }
    
    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/v1/iiif/id1/canvas/p1");
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
