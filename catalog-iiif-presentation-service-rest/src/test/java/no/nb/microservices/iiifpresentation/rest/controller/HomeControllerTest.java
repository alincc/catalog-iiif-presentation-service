package no.nb.microservices.iiifpresentation.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;
import no.nb.microservices.iiifpresentation.model.LabelValue;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.service.IManifestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    private IManifestService manifestService;
    
    private HomeController homeController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        homeController = new HomeController(manifestService);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        Manifest manifest = new Manifest();
        manifest.setId("http://localhost/12345/manifest");
        manifest.setLabel("Title");
        manifest.setContext("http://iiif.io/api/presentation/2/context.json");
        manifest.setType("sc:Manifest");
        List<LabelValue> metadata = new ArrayList<>();
        metadata.add(new LabelValue("Publisher", "JUNIT TEST"));
        manifest.setMetadata(metadata);
        
        when(manifestService.getManifest("12345", "http://localhost/12345/manifest")).thenReturn(manifest);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/12345/manifest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/12345/manifest"))
            .andExpect(jsonPath("$.@type").value("sc:Manifest"))
            .andExpect(jsonPath("$.@context").value("http://iiif.io/api/presentation/2/context.json"))
            .andExpect(jsonPath("$.label").value("Title"))
            .andExpect(jsonPath("$.metadata[*].label").value("Publisher"))
            .andExpect(jsonPath("$.metadata[*].value").value("JUNIT TEST"))
            .andReturn();
    }
    
    @Test
    public void testDefaultHandler() throws Exception {
        when(manifestService.getManifest("12345", "http://localhost/12345/manifest")).thenThrow(new RetrieveItemException("JUnit test exception"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/12345/manifest"))
            .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
    
}
