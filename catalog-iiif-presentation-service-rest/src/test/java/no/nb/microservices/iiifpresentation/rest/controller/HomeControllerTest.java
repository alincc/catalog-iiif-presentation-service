package no.nb.microservices.iiifpresentation.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.core.manifest.ManifestService;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    private ManifestService manifestService;
    
    private HomeController homeController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        homeController = new HomeController(manifestService);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, null));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/id1/manifest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/catalog/iiif/id1/manifest"))
            .andReturn();
    }
    
    @Test
    public void testDefaultHandler() throws Exception {
        when(manifestService.getManifest("12345")).thenThrow(new RetrieveItemException("JUnit test exception"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/catalog/iiif/12345/manifest"))
            .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
    
}
