package no.nb.microservices.iiifpresentation.core.manifest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.config.ApplicationSettings;
import no.nb.microservices.iiifpresentation.core.item.ItemService;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;

public class ManifestServiceImplTest {

    @Mock
    private ItemService itemService;
    
    @Mock
    private ApplicationSettings applicationSettings;
    
    @InjectMocks
    private ManifestServiceImpl manifestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getManifestTest() throws Exception {
        String id = "id1";
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
                return new ItemResource(id);
            }

            @Override
            public ItemResource get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        when(itemService.getItemByIdAsync(id)).thenReturn(itemFuture);

        ItemStructPair itemAndStructPair = manifestService.getManifest(id);

        // Asserts
        assertNotNull("Should have a item", itemAndStructPair.getItem());
        verify(itemService, times(1)).getItemByIdAsync(id);

    }
    
    @Test(expected=RetrieveItemException.class)
    public void testGetManifestGetItemByAsyncInterruptedException() throws InterruptedException {
        String id = "id1";
        when(itemService.getItemByIdAsync(id)).thenThrow(InterruptedException.class);
        manifestService.getManifest(id);
        verify(itemService);
    }
    
    @Test(expected=RetrieveItemException.class)
    public void testGetManifestGetItemByAsyncExecutionException() throws InterruptedException, ExecutionException {
        String id = "id1";
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
        when(itemService.getItemByIdAsync(id)).thenReturn(itemFuture);
        manifestService.getManifest(id);
        verify(itemService);
    }
}
