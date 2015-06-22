package no.nb.microservices.iiifpresentation.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogitem.rest.model.Person;
import no.nb.microservices.catalogitem.rest.model.Role;
import no.nb.microservices.catalogitem.rest.model.TitleInfo;
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
        String id = "1";
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
                Metadata metadata = new Metadata();
                TitleInfo titleInfo = new TitleInfo();
                titleInfo.setTitle("Title ID1");
                metadata.setTitleInfo(titleInfo);
                List<Person> people = new ArrayList<>();
                Person person = new Person();
                person.setDate("1960");
                person.setName("Person 1");
                List<Role> roles = new ArrayList<>();
                Role role = new Role();
                role.setName("Creator");
                roles.add(role);
                person.setRoles(roles);
                people.add(person);
                metadata.setPeople(people);
                ItemResource itemResource = new ItemResource("id1");
                itemResource.setMetadata(metadata);
                return itemResource;
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
        assertEquals("Title ID1", manifest.getLabel());
        assertEquals("http://iiif.io/api/presentation/2/context.json", manifest.getContext());
        assertEquals("sc:Manifest", manifest.getType());
        assertEquals("http://catalog-iiif-presentation-service.nb.no/iiif/" + id + "/manifest", manifest.getId());
        assertEquals("Creator", manifest.getMetadata().get(0).getLabel());
        assertEquals("Person 1", manifest.getMetadata().get(0).getValue());
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
