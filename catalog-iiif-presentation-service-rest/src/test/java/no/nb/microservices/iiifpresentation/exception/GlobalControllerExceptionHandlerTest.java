package no.nb.microservices.iiifpresentation.exception;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

public class GlobalControllerExceptionHandlerTest {

    private GlobalControllerExceptionHandler handler;
    private HttpServletResponse response;
    
    @Before
    public void setUp() {
        handler = new GlobalControllerExceptionHandler();
        response = new MockHttpServletResponse();
    }
    
    @Test
    public void testItemNotFound() throws Exception {
        handler.handleAnnotationNotFound(response);
        
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testCanvasNotFound() throws Exception {
        handler.handleAnnotationNotFound(response);
        
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testAnnotationNotFound() throws Exception {
        handler.handleAnnotationNotFound(response);
        
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testHotspotNotFound() throws Exception {
        handler.handleHotspotNotFound(response);
        
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

}
