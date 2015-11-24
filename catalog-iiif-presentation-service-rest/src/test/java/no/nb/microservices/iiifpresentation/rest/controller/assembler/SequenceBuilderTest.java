package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.model.Sequence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
                .withManifestId("id1")
                .withStruct(TestStructMap.aDefaultStructMap().build())
                .build();

        assertNotNull("http://localhost/v1/catalog/iiif/id1/sequence/normal", sequence.getId());
    }

    @Test(expected=IllegalStateException.class)
    public void whenIdIsMissingThenthrowException() {
        new SequenceBuilder().build();
    }
    
    @Test
    public void sequenceMustHaveAType() {
        Sequence sequence = TestSequence.aDefaultSequence().build();
        
        assertEquals("sc:Sequence", sequence.getType());
    }

    @Test
    public void sequenceMustHaveALabel() {
        Sequence sequence = TestSequence.aDefaultSequence().build();
        
        assertEquals("Current Page Order", sequence.getLabel());
    }

    @Test
    public void sequenceMustHaveACanvas() {
        Sequence sequence = TestSequence.aDefaultSequence().build();
        
        assertEquals(15, sequence.getCanvases().size());
    }

    private void createDefaultRequestAttributes() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/catalog/iiif/id1/manifest");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }
    
    private void resetRequestAttributes() {
        RequestContextHolder.resetRequestAttributes();
    }

}
