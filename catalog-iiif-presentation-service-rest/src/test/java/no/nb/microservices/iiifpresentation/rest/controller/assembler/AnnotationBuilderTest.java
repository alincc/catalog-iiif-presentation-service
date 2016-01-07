package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Resource;

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
        Annotation annotation = TestAnnotation.aMinimalAnnotation().withId("annoId").build();

        assertEquals("annoId", annotation.getId());
    }

    @Test(expected=IllegalStateException.class)
    public void whenMissingManifestIdThenThrowException() {
        TestAnnotation.aMinimalAnnotation().withId(null).build();
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
