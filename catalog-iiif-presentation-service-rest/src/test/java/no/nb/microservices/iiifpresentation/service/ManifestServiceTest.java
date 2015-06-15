package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogitem.rest.model.TitelInfo;
import no.nb.microservices.iiifpresentation.Application;
import no.nb.microservices.iiifpresentation.model.Manifest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by andreasb on 15.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ManifestServiceTest {

    @Mock
    private ItemService itemService;

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

        // Call
        Manifest manifest = manifestService.getManifest(id);

        // Asserts
        assertEquals("Donald Ducks great adventure", manifest.getLabel());
        verify(itemService, times(1)).getItemByIdAsync(id);

    }
}
