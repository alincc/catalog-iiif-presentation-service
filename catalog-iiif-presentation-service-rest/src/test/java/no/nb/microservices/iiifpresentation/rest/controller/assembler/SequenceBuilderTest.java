package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.model.Sequence;

public class SequenceBuilderTest {

    @Before
    public void init() {
        createDefaultRequestAttributes();
    }


    @After
    public void cleanUp() {
        resetRequestAttributes();
    }
    
    @Test
    public void whenIdThenCreateContext() {
        Sequence sequence = new SequenceBuilder()
                .withId("id1")
                .withStruct(TestStructMap.aDefaultStructMap().build())
                .build();

        assertNotNull("http://localhost/catalog/iiif/id1/sequence/normal", sequence.getId());
    }

    @Test(expected=IllegalStateException.class)
    public void whenIdIsMissingThenthrowException() {
        new SequenceBuilder().build();
    }
    
    @Test
    public void sequenceMustHaveAType() {
        Sequence sequence = new SequenceFixtures().SEQUENCE;
        
        assertEquals("sc:Sequence", sequence.getType());
    }

    @Test
    public void sequenceMustHaveALabel() {
        Sequence sequence = new SequenceFixtures().SEQUENCE;
        
        assertEquals("Current Page Order", sequence.getLabel());
    }

    @Test
    public void sequenceMustHaveACanvas() {
        Sequence sequence = new SequenceFixtures().SEQUENCE;
        
        assertEquals(10, sequence.getCanvases().size());
    }

    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/iiif/id1/manifest");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
    
    private void resetRequestAttributes() {
        RequestContextHolder.resetRequestAttributes();
    }

}
