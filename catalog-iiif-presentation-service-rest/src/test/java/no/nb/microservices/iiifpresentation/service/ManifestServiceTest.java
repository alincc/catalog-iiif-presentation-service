package no.nb.microservices.iiifpresentation.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogitem.rest.model.TitelInfo;
import no.nb.microservices.iiifpresentation.config.ApplicationSettings;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;
import no.nb.microservices.iiifpresentation.model.Manifest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by andreasb on 15.06.15.
 */
public class ManifestServiceTest {

    @Mock
    private ItemService itemService;
    
    @Mock
    private ApplicationSettings applicationSettings;
    
    @InjectMocks
    private ManifestService manifestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getManifestTest() throws Exception {
        // Setup
        String id = "1234";
        Future<ItemResource> itemFuture = new Future<ItemResource>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public ItemResource get() throws InterruptedException, ExecutionException {
                ItemResource item = new ItemResource();
                item.setMetadata(new Metadata());
                TitelInfo titleInfo = new TitelInfo();
                titleInfo.setTitle("Donald Ducks great adventure");
                item.getMetadata().setTitleInfo(titleInfo);
                return item;
            }

            @Override
            public ItemResource get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };

        // Mocks
        when(itemService.getItemByIdAsync(id)).thenReturn(itemFuture);
        when(applicationSettings.getContextUrl()).thenReturn("http://iiif.io/api/presentation/2/context.json");

        // Call
        Manifest manifest = manifestService.getManifest(id, "http://catalog-iiif-presentation-service.nb.no/iiif/" + id + "/manifest");

        // Asserts
        assertEquals("Donald Ducks great adventure", manifest.getLabel());
        assertEquals("http://iiif.io/api/presentation/2/context.json", manifest.getContext());
        assertEquals("sc:Manifest", manifest.getType());
        assertEquals("http://catalog-iiif-presentation-service.nb.no/iiif/" + id + "/manifest", manifest.getId());
        verify(itemService, times(1)).getItemByIdAsync(id);

    }
    
    @Test(expected=RetrieveItemException.class)
    public void testGetManifestGetItemByAsyncInterruptedException() throws InterruptedException {
        when(itemService.getItemByIdAsync("123")).thenThrow(InterruptedException.class);
        manifestService.getManifest("123", "http://catalog-iiif-presentation-service.nb.no/iiif/123/manifest");
        verify(itemService);
    }
    
    @Test(expected=RetrieveItemException.class)
    public void testGetManifestGetItemByAsyncExecutionException() throws InterruptedException, ExecutionException {
        Future<ItemResource> itemFuture = new Future<ItemResource>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public ItemResource get() throws InterruptedException, ExecutionException {
                throw new ExecutionException(null);
            }

            @Override
            public ItemResource get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        when(itemService.getItemByIdAsync("123")).thenReturn(itemFuture);
        manifestService.getManifest("123", "http://catalog-iiif-presentation-service.nb.no/iiif/123/manifest");
        verify(itemService);
    }
}
