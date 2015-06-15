package no.nb.microservices.iiifpresentation.rest.controller;

import no.nb.microservices.iiifpresentation.Application;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.service.ManifestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class HomeControllerTest {

    @Mock
    private ManifestService manifestService;

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void getManifestTest() throws Exception{
        // Setup
        Manifest manifest = new Manifest();
        manifest.setId("78234");

        // Mocks
        when(manifestService.getManifest("78234")).thenReturn(manifest);

        // Calls and Asserts
        mockMvc.perform(get("/78234/manifest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.@id").value(manifest.getId()));
    }

}
