package no.nb.microservices.iiifpresentation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogitem.rest.model.Person;
import no.nb.microservices.catalogitem.rest.model.Role;
import no.nb.microservices.catalogitem.rest.model.TitleInfo;
import no.nb.microservices.iiifpresentation.repository.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    
    private ItemService itemService;
    
    @Before
    public void setup() {
        itemService = new ItemService(itemRepository);
    }
    
    @Test
    public void getItemByIdAsync() throws InterruptedException, ExecutionException {
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
        
        when(itemRepository.getById("id1")).thenReturn(itemResource);
        Future<ItemResource> futureItem = itemService.getItemByIdAsync("id1");
        
        // com.netflix.hystrix.contrib.javanica.command.AsyncResult only have invoke as a supported operation
        ItemResource itemResource2 = ((AsyncResult<ItemResource>)futureItem).invoke();
        assertNotNull("ItemResource should not be null", itemResource2);
        assertEquals("Should be Title ID1", "Title ID1", itemResource2.getMetadata().getTitleInfo().getTitle());
        assertEquals("Should be Person 1", "Person 1", itemResource2.getMetadata().getPeople().get(0).getName());
        assertEquals("Should be 1960", "1960", itemResource2.getMetadata().getPeople().get(0).getDate());
        assertEquals("Should be Creator", "Creator", itemResource2.getMetadata().getPeople().get(0).getRoles().get(0).getName());
        
        verify(itemRepository).getById("id1");
    }
}
