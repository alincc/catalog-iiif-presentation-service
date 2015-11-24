package no.nb.microservices.iiifpresentation.core.manifest;

import no.nb.commons.web.util.UserUtils;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.core.item.ItemRepository;
import no.nb.microservices.iiifpresentation.core.metadata.repository.MetadataRepository;
import no.nb.microservices.iiifpresentation.exception.ItemNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ManifestServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private MetadataRepository metadataRepository;
    
    @InjectMocks
    private ManifestServiceImpl manifestService;

    @Before
    public void setup() {
        mockRequest();
    }
    
    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }
    
    @Test
    public void getManifestTest() throws Exception {
        String id = "id1";
        when(itemRepository.getById(eq(id), anyString(), anyString(), anyString(), anyString())).thenReturn(new ItemResource());
        when(metadataRepository.getStructById(eq(id), anyString(), anyString(), anyString(), anyString())).thenReturn(new StructMap());
        
        ItemStructPair itemAndStructPair = manifestService.getManifest(id);

        assertNotNull("Should have a item", itemAndStructPair.getItem());
        assertNotNull("Should have a struct", itemAndStructPair.getStruct());
    }
    
    @Test(expected=ItemNotFoundException.class)
    public void testExceptionhandling() throws InterruptedException {
        String id = "id1";
        when(itemRepository.getById(eq(id), anyString(), anyString(), anyString(), anyString())).thenReturn(new ItemResource());
        when(metadataRepository.getStructById(eq(id), anyString(), anyString(), anyString(), anyString())).thenThrow(RuntimeException.class);
        
        manifestService.getManifest(id);
    }
    
    private void mockRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/catalog/iiif/id1/manifest");
        String ip = "123.45.123.123";
        request.addHeader(UserUtils.REAL_IP_HEADER, ip);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RequestContextHolder.setRequestAttributes(attributes);
    }    
    
    
}
