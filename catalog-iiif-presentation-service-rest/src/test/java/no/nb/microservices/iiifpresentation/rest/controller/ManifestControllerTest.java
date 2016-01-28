package no.nb.microservices.iiifpresentation.rest.controller;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
import no.nb.microservices.iiifpresentation.model.Canvas;
import no.nb.microservices.iiifpresentation.model.IiifPresentationContext;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Sequence;

@RunWith(MockitoJUnitRunner.class)
public class ManifestControllerTest {

    @Mock
    private ManifestService manifestService;
    
    @InjectMocks
    private ManifestController manifestController;
    
    private MockHttpServletRequest request;
    
    @Before
    public void setup() {
        mockRequest();
    }
    
    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }
    
    @Test
    public void testGetManifest() throws Exception {
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        ResponseEntity<Manifest> response = manifestController.getManifest("id1", MediaType.APPLICATION_JSON_VALUE);
        
        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getHeaders().get("Content-Type"), hasItems("application/json;charset=UTF-8"));
        assertThat(response.getHeaders().get("Link"), hasItems("<http://iiif.io/api/presentation/2/context.json>;rel=\"http://www.w3.org/ns/json-ld#context\";type=\"application/ld+json\""));
        assertThat(response.getBody().getId(), is("http://localhost/catalog/v1/iiif/id1/manifest"));
    }

    @Test
    public void testManifestJsonLdAcceptHeader() throws Exception {
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        ResponseEntity<Manifest> response = manifestController.getManifest("id1", "application/ld+json");
        
        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getHeaders().get("Content-Type"), hasItems("application/ld+json;charset=UTF-8"));
        assertThat(response.getHeaders().get("Link"), nullValue());
        assertThat(response.getBody().getId(), is("http://localhost/catalog/v1/iiif/id1/manifest"));
    }

    @Test
    public void testGetSequence() throws Exception {
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        ResponseEntity<Sequence> response = manifestController.getSequence("id1", MediaType.APPLICATION_JSON_VALUE);
        
        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getBody().getId(), is("http://localhost/catalog/v1/iiif/id1/sequence/normal"));
    }

    @Test
    public void testSequenceJsonLdAcceptHeader() throws Exception {
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, createDefaultStructMap()));
        
        ResponseEntity<Sequence> response = manifestController.getSequence("id1", "application/ld+json");
        
        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getHeaders().get("Content-Type"), hasItems("application/ld+json;charset=UTF-8"));
        assertThat(response.getHeaders().get("Link"), nullValue());
    }

    @Test
    public void testGetCanvas() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        ResponseEntity<Canvas> response = manifestController.getCanvas("id1", "URN:NBN:no-nb_digibok_2001010100001_TEST", MediaType.APPLICATION_JSON_VALUE);

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getBody().getId(), is("http://localhost/catalog/v1/iiif/id1/canvas/URN:NBN:no-nb_digibok_2001010100001_TEST"));
    }

    @Test
    public void testCanvasJsonLdAcceptHeader() throws Exception {
        StructMap structMap = createDefaultStructMap();
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));

        ResponseEntity<Canvas> response = manifestController.getCanvas("id1", "URN:NBN:no-nb_digibok_2001010100001_C1", "application/ld+json");

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getHeaders().get("Content-Type"), hasItems("application/ld+json;charset=UTF-8"));
        assertThat(response.getHeaders().get("Link"), nullValue());
    }


    @Test
    public void testGetAnnotation() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        ResponseEntity<Annotation> response = manifestController.getAnnotation("id1", "URN:NBN:no-nb_digibok_2001010100001_TEST", MediaType.APPLICATION_JSON_VALUE);

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getBody().getId(), is("http://localhost/catalog/v1/iiif/id1/annotation/URN:NBN:no-nb_digibok_2001010100001_TEST"));
    }
    
    @Test
    public void testAnnotationJsonLdAcceptHeader() throws Exception {
        StructMap structMap = createDefaultStructMap();
        Div div = TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build();
        structMap.addDiv(div);
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));
        
        ResponseEntity<Annotation> response = manifestController.getAnnotation("id1", "URN:NBN:no-nb_digibok_2001010100001_TEST", "application/ld+json");

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(response.getHeaders().get("Content-Type"), hasItems("application/ld+json;charset=UTF-8"));
        assertThat(response.getHeaders().get("Link"), nullValue());
    }

    @Test
    public void testGetHotspots() throws Exception {
        StructMap structMap = createDefaultStructMap();
        structMap.addDiv(TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build());
        structMap.addDiv(createDivWithHotspot());
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));

        ResponseEntity<AnnotationList> response = manifestController.getHotspots("id1", "URN:NBN:no-nb_digibok_2001010100001_0099", MediaType.APPLICATION_JSON_VALUE);
        
        AnnotationList annotationList = response.getBody();
        assertThat(annotationList.getResources().size(), is(1));
        assertThat(annotationList.getContext(), is(new IiifPresentationContext().getContext()));
        Annotation annotation = annotationList.getResources().iterator().next();
        assertThat(annotation.getContext(), is(new NullContext().getContext()));
        assertThat(annotation.getId(), is("http://localhost/catalog/v1/iiif/id1/hotspots/URN:NBN:no-nb_digibok_2001010100001_0099/1_2_3"));
        assertThat(annotation.getResource(), notNullValue());
        assertThat(annotation.getResource().getId(), is("http://localhost/catalog/v1/iiif/URN_5_6/canvas/URN_5_6_7"));
    }

    @Test
    public void testGetHotspot() throws Exception {
        StructMap structMap = createDefaultStructMap();
        structMap.addDiv(TestDiv.aDefaultDiv()
                .withPageNumber("TEST")
                .build());
        Div divWithHotspot = createDivWithHotspot();
        structMap.addDiv(divWithHotspot);
        when(manifestService.getItemAndStruct("id1")).thenReturn(new ItemStructPair(null, structMap));

        ResponseEntity<Annotation> response = manifestController.getHotspot("id1", "URN:NBN:no-nb_digibok_2001010100001_0099", "1_2_3", MediaType.APPLICATION_JSON_VALUE);

        Annotation annotation = response.getBody();
        assertThat(annotation.getContext(), is(new IiifPresentationContext().getContext()));
        assertThat(annotation.getId(), is("http://localhost/catalog/v1/iiif/id1/hotspots/URN:NBN:no-nb_digibok_2001010100001_0099/1_2_3"));
        assertThat(annotation.getResource(), notNullValue());
        assertThat(annotation.getResource().getId(), is("http://localhost/catalog/v1/iiif/URN_5_6/canvas/URN_5_6_7"));
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
        hs.setHsId("URN_5_6_7");
        hs.setValue("Summary");
        hotspot.setHs(hs);
        divWithHotspot.getHotspots().add(hotspot);
        return divWithHotspot;
    }
    
    private void mockRequest() {
        request = new MockHttpServletRequest("GET", "/catalog/v1/search?q=Junit");
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
