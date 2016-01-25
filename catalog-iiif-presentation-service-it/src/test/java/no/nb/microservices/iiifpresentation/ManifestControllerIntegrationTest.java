package no.nb.microservices.iiifpresentation;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import no.nb.commons.web.util.UserUtils;
import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.Hotspot;
import no.nb.microservices.catalogmetadata.model.struct.Hs;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.catalogmetadata.test.struct.DivBuilder;
import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.model.Sequence;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, RibbonClientConfiguration.class})
@WebIntegrationTest("server.port:0")
public class ManifestControllerIntegrationTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    ILoadBalancer lb;

    RestTemplate template = new TestRestTemplate();
    
    MockWebServer server;
    
    @Before
    public void setup() throws Exception {

        server = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            String itemId1Mock = IOUtils.toString(this.getClass().getResourceAsStream("catalog-item-service-id1.json"));

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().startsWith("/catalog/v1/items/id1")) {
                    return new MockResponse().setBody(itemId1Mock).setHeader("Content-Type", "application/hal+json; charset=utf-8");
                } else if (request.getPath().startsWith("/catalog/v1/metadata/id1/struct")) {
                    StructMap struct = TestStructMap.aDefaultStructMap().build();
                    Div divWithHotspot = new DivBuilder().withPageNumber("99").build();
                    Hotspot hotspot = new Hotspot();
                    hotspot.setB(1000);
                    hotspot.setL(2000);
                    hotspot.setR(3000);
                    hotspot.setT(4000);
                    hotspot.setHszId("1_2_3");
                    Hs hs = new Hs();
                    hs.setHsId("URN");
                    hs.setValue("Summary");
                    hotspot.setHs(hs);
                    divWithHotspot.getHotspots().add(hotspot);
                    struct.addDiv(divWithHotspot);
                    String structMap = TestStructMap.structMapToString(struct);
                    return new MockResponse().setBody(structMap).setHeader("Content-Type", "application/xml; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }

        };
        server.setDispatcher(dispatcher);
        server.start();

        BaseLoadBalancer blb = (BaseLoadBalancer) lb;
        blb.setServersList(Arrays.asList(new Server(server.getHostName(), server.getPort())));
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        HttpHeaders headers = createDefaultHeaders();        
        
        ResponseEntity<Manifest> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/manifest", HttpMethod.GET,
                new HttpEntity<Void>(headers), Manifest.class);
        Manifest manifest = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Manifest should not be null", manifest);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", manifest.getContext());
        assertEquals("Manifest label should be", "Title ID1", manifest.getLabel());
        assertEquals("manifest should have one sequence", 1, manifest.getSequences().size());
    }

    @Test
    public void testGetSequence() throws Exception {
        HttpHeaders headers = createDefaultHeaders();
        
        ResponseEntity<Sequence> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/sequence/normal", HttpMethod.GET,
                new HttpEntity<Void>(headers), Sequence.class);
        Sequence sequence = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Sequence should not be null", sequence);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", sequence.getContext());
        assertEquals("Should have a type", "sc:Sequence", sequence.getType());
    }

    @Test
    public void testGetCanvas() throws Exception {
        HttpHeaders headers = createDefaultHeaders();
        
        ResponseEntity<Canvas> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/canvas/URN:NBN:no-nb_digibok_2001010100001_C1", HttpMethod.GET,
                new HttpEntity<Void>(headers), Canvas.class);
        Canvas canvas = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Canvas should not be null", canvas);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", canvas.getContext());
        assertEquals("Should have a type", "sc:Canvas", canvas.getType());
    }

    @Test
    public void testGetAnnotation() throws Exception {
        HttpHeaders headers = createDefaultHeaders();
        
        ResponseEntity<Annotation> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/annotation/URN:NBN:no-nb_digibok_2001010100001_0001", HttpMethod.GET,
                new HttpEntity<Void>(headers), Annotation.class);
        Annotation annotation = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Annotation should not be null", annotation);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", annotation.getContext());
        assertEquals("Should have a type", "oa:Annotation", annotation.getType());
    }

    @Test
    public void testGetHotspots() throws Exception {
        HttpHeaders headers = createDefaultHeaders();
        
        ResponseEntity<Annotation> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/hotspots/DIV1", HttpMethod.GET,
                new HttpEntity<Void>(headers), Annotation.class);
        Annotation annotation = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Annotation should not be null", annotation);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", annotation.getContext());
        assertEquals("Should have a type", "sc:AnnotationList", annotation.getType());
    }

    @Test
    public void testGetHotspot() throws Exception {
        HttpHeaders headers = createDefaultHeaders();
        
        ResponseEntity<Annotation> response = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/catalog/v1/iiif/id1/hotspots/DIV99/1_2_3", HttpMethod.GET,
                new HttpEntity<Void>(headers), Annotation.class);
        Annotation annotation = response.getBody();
        
        assertTrue("Repsonse code should be successful", response.getStatusCode().is2xxSuccessful());
        assertNotNull("Annotation should not be null", annotation);
        assertEquals("Should hava a context", "http://iiif.io/api/presentation/2/context.json", annotation.getContext());
        assertEquals("Should have a type", "oa:Annotation", annotation.getType());
    }

    private HttpHeaders createDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(UserUtils.SSO_HEADER, "token");
        headers.add(UserUtils.REAL_IP_HEADER, "123.45.100.1");
        return headers;
    }

}

@Configuration
class RibbonClientConfiguration {

    @Bean
    public ILoadBalancer ribbonLoadBalancer() {
        return new BaseLoadBalancer();
    }
}