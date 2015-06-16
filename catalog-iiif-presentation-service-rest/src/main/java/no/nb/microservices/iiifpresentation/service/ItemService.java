package no.nb.microservices.iiifpresentation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by andreasb on 15.06.15.
 */
@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //@CacheResult TODO: This fails when uncommented
    @HystrixCommand(fallbackMethod = "getDefaultItem")
    public Future<ItemResource> getItemByIdAsync(String id) throws InterruptedException {
        LOGGER.info("Fetching item from catalog-item-service by id " + id);
        return new AsyncResult<ItemResource>(itemRepository.getById(id));
    }

    private ItemResource getDefaultItem(String id) {
        LOGGER.warn("Failed to get item from catalog-item-service. Returning default item with id " + id);
        ItemResource item = new ItemResource();
        return item;
    }
}
