package no.nb.microservices.iiifpresentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, RibbonClientConfiguration.class})
@WebIntegrationTest("server.port:0")
public class HomeControllerIntegrationTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    
    @Autowired
    ILoadBalancer lb;

    RestTemplate template = new TestRestTemplate();
    
    MockWebServer server;
    
    @Before
    public void setup() throws Exception {
        String itemId1Mock = IOUtils.toString(this.getClass().getResourceAsStream("catalog-item-service-id1.json"));

        server = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().equals("/catalog/items/id1")){
                    return new MockResponse().setBody(itemId1Mock).setHeader("Content-Type", "application/hal+json; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server.setDispatcher(dispatcher);
        server.start();

        BaseLoadBalancer blb = (BaseLoadBalancer) lb;
        blb.setServersList(Arrays.asList(new Server(server.getHostName(), server.getPort())));
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
    
    @Test
    public void testGetManifestFromItemService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/id1/manifest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/id1/manifest"))
            .andExpect(jsonPath("$.@type").value("sc:Manifest"))
            .andExpect(jsonPath("$.@context").value("http://iiif.io/api/presentation/2/context.json"))
            .andExpect(jsonPath("$.label").value("Title ID1"))
            .andExpect(jsonPath("$.metadata[0].label").value("Author"))
            .andExpect(jsonPath("$.metadata[0].value").value("Person 1"))
//            .andExpect(jsonPath("$.metadata[1].label").value("Publisher"))
//            .andExpect(jsonPath("$.metadata[1].value").value("Publisher 1"))
            
            .andReturn();
    }
    
    @Test
    public void testGetManifestFromFallBackMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/id2/manifest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/id2/manifest"))
            .andExpect(jsonPath("$.@type").value("sc:Manifest"))
            .andExpect(jsonPath("$.@context").value("http://iiif.io/api/presentation/2/context.json"))
            .andExpect(jsonPath("$.label").value("Untitled"))
            .andReturn();
    }
}

@Configuration
class RibbonClientConfiguration {

    @Bean
    public ILoadBalancer ribbonLoadBalancer() {
        return new BaseLoadBalancer();
    }
}