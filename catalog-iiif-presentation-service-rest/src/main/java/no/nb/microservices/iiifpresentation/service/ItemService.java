package no.nb.microservices.iiifpresentation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by andreasb on 15.06.15.
 */
@Service
public class ItemService {
    ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @CacheResult
    @HystrixCommand(fallbackMethod = "getDefaultMods")
    public Future<ItemResource> getItemByIdAsync(String id) throws InterruptedException {
        return new AsyncResult<ItemResource>(itemRepository.getById(id));
    }

    private ItemResource getDefaultMods(String id) {
        ItemResource item = new ItemResource();
        return item;
    }
}
