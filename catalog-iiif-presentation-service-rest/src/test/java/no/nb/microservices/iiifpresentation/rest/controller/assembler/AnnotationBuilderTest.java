package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.model.struct.Resource;
import no.nb.microservices.iiifpresentation.model.Annotation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;

public class AnnotationBuilderTest {

    @Before
    public void init() {
        createDefaultRequestAttributes();
    }

    @After
    public void cleanUp() {
        resetRequestAttributes();
    }

    @Test
    public void annotationMustHaveId() {
        Annotation annotation = createDefaultAnnotation();
        
        assertEquals("http://localhost/v1/catalog/iiif/m1/annotation/h1", annotation.getId());
    }

    @Test
    public void annotationMustHaveOn() {
        Annotation annotation = createDefaultAnnotation();
        
        assertEquals("http://localhost/v1/catalog/iiif/m1/canvas/c1", annotation.getOn());
    }

    @Test(expected=IllegalStateException.class)
    public void whenMissingManifestIdThenThrowException() {
        TestAnnotation.aMinimalAnnotation().withManifestId(null).build();
    }
    
    @Test(expected=IllegalStateException.class)
    public void whenMissingCanvasIdThenThrowException() {
        TestAnnotation.aMinimalAnnotation().withCanvasId(null).build();
    }

    @Test(expected=IllegalStateException.class)
    public void whenMissingResourceHrefThenThrowException() {
        Resource resource = new Resource();
        TestAnnotation.aMinimalAnnotation().withResource(resource).build();
    }

    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/catalog/iiif/id1/annotation/p1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
    
    private void resetRequestAttributes() {
        RequestContextHolder.resetRequestAttributes();
    }

    private Annotation createDefaultAnnotation() {
        return TestAnnotation.aMinimalAnnotation().build();
    }
}
