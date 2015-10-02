package no.nb.microservices.iiifpresentation.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.commons.web.util.UserUtils;
import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.catalogmetadata.test.struct.TestDiv;
import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.core.manifest.ManifestService;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;

@RunWith(MockitoJUnitRunner.class)
public class ManifestControllerTest {

    @Mock
    private ManifestService manifestService;
    
    @InjectMocks
    private ManifestController homeController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockRequest();
    }
    
    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/id1/manifest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/catalog/iiif/id1/manifest"))
            .andReturn();
    }

    @Test
    public void testGetSequence() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/id1/sequence/normal"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/catalog/iiif/id1/sequence/normal"))
            .andReturn();
    }

    @Test
    public void testGetCanvas() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/id1/canvas/DIVTEST"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/catalog/iiif/id1/canvas/DIVTEST"))
            .andReturn();
    }
    
    
    @Test
    public void testDefaultHandler() throws Exception {
        when(manifestService.getManifest("12345")).thenThrow(new RetrieveItemException("JUnit test exception"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/12345/manifest"))
            .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
    
    private void mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/search?q=Junit");
        String ip = "123.45.123.123";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RequestContextHolder.setRequestAttributes(attributes);
    }    

    private StructMap createDefaultStructMap() {
        return TestStructMap.aDefaultStructMap().build();
    }
        
}
