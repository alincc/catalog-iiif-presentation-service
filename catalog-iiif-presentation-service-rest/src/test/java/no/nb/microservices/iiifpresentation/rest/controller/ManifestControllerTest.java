package no.nb.microservices.iiifpresentation.rest.controller;

import no.nb.commons.web.util.UserUtils;
import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.Hotspot;
import no.nb.microservices.catalogmetadata.model.struct.Hs;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.catalogmetadata.test.struct.DivBuilder;
import no.nb.microservices.catalogmetadata.test.struct.TestDiv;
import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.core.manifest.ManifestService;
import no.nb.microservices.iiifpresentation.model.Annotation;
import no.nb.microservices.iiifpresentation.model.AnnotationList;
import no.nb.microservices.iiifpresentation.model.IiifPresentationContext;
import no.nb.microservices.iiifpresentation.model.NullContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ManifestControllerTest {

    @Mock
    private ManifestService manifestService;
    
    @InjectMocks
    private ManifestController manifestController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(manifestController).build();
        mockRequest();
    }
    
    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        MvcResult andReturn = mockMvc.perform(get("/v1/catalog/iiif/id1/manifest"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
            .andExpect(header().string("Link", "<http://iiif.io/api/presentation/2/context.json>;rel=\"http://www.w3.org/ns/json-ld#context\";type=\"application/ld+json\""))
            .andExpect(jsonPath("$.@id").value("http://localhost/v1/catalog/iiif/id1/manifest"))
            .andReturn();
        
        System.out.println(andReturn.getResponse().getContentAsString());
    }

    @Test
    public void testManifestJsonLdAcceptHeader() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/manifest")
                .accept("application/ld+json"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/ld+json;charset=UTF-8"))
            .andExpect(header().doesNotExist("Link"))
            .andExpect(jsonPath("$.@id").value("http://localhost/v1/catalog/iiif/id1/manifest"))
            .andReturn();
    }

    @Test
    public void testGetSequence() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/sequence/normal"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/v1/catalog/iiif/id1/sequence/normal"))
            .andReturn();
    }

    @Test
    public void testSequenceJsonLdAcceptHeader() throws Exception {
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/sequence/normal")
                .accept("application/ld+json"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/ld+json;charset=UTF-8"))
            .andExpect(header().doesNotExist("Link"))
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
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/canvas/DIVTEST"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/v1/catalog/iiif/id1/canvas/DIVTEST"))
            .andReturn();
    }

    @Test
    public void testCanvasJsonLdAcceptHeader() throws Exception {
        StructMap structMap = createDefaultStructMap();
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/canvas/DIV1")
                .accept("application/ld+json"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/ld+json;charset=UTF-8"))
            .andExpect(header().doesNotExist("Link"))
            .andReturn();
    }


    @Test
    public void testGetAnnotation() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/annotation/URN:NBN:no-nb_digibok_2001010100001_TEST"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.@id").value("http://localhost/v1/catalog/iiif/id1/annotation/URN:NBN:no-nb_digibok_2001010100001_TEST"))
            .andReturn();
    }

    @Test
    public void testGetHotspots() throws Exception {
        StructMap structMap = createDefaultStructMap();
        structMap.addDiv(TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build());
        structMap.addDiv(createDivWithHotspot());
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));

        ResponseEntity<AnnotationList> response = manifestController.getHotspots("id1", "DIV99", MediaType.APPLICATION_JSON_VALUE);
        
        AnnotationList annotationList = response.getBody();
        assertThat(annotationList.getResources().size(), is(1));
        assertThat(annotationList.getContext(), is(new IiifPresentationContext().getContext()));
        Annotation annotation = annotationList.getResources().iterator().next();
        assertThat(annotation.getContext(), is(new NullContext().getContext()));
        assertThat(annotation.getId(), is("http://localhost/v1/catalog/iiif/id1/hotspots/DIV99/1_2_3"));
        assertThat(annotation.getResource(), notNullValue());
        assertThat(annotation.getResource().getId(), is("URN"));
    }

    @Test
    public void testGetHotspot() throws Exception {
        StructMap structMap = createDefaultStructMap();
        structMap.addDiv(TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build());
        Div divWithHotspot = createDivWithHotspot();
        structMap.addDiv(divWithHotspot);
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));

        ResponseEntity<Annotation> response = manifestController.getHotspot("id1", "DIV99", "1_2_3", MediaType.APPLICATION_JSON_VALUE);
        
        Annotation annotation = response.getBody();
        assertThat(annotation.getContext(), is(new IiifPresentationContext().getContext()));
        assertThat(annotation.getId(), is("http://localhost/v1/catalog/iiif/id1/hotspots/DIV99/1_2_3"));
        assertThat(annotation.getResource(), notNullValue());
        assertThat(annotation.getResource().getId(), is("URN"));
        assertThat(annotation.getResource().getDescription(), is(divWithHotspot.getHotspots().get(0).getHs().getValue()));
    }
    
    private Div createDivWithHotspot() {
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
        return divWithHotspot;
    }
    
    @Test
    public void testAnnotationJsonLdAcceptHeader() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getManifest("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        mockMvc.perform(get("/v1/catalog/iiif/id1/annotation/URN:NBN:no-nb_digibok_2001010100001_TEST")
                .accept("application/ld+json"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/ld+json;charset=UTF-8"))
            .andExpect(header().doesNotExist("Link"))
            .andReturn();
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
