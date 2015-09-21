package no.nb.microservices.iiifpresentation.core.item;

import java.util.concurrent.Future;

import no.nb.microservices.catalogitem.rest.model.ItemResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@Service
public class ItemService implements IItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //@CacheResult TODO: This fails when uncommented
    @Override
    @HystrixCommand(fallbackMethod = "getDefaultItem")
    public Future<ItemResource> getItemByIdAsync(String id) throws InterruptedException {
        LOGGER.info("Fetching item from catalog-item-service by id " + id);
        return new AsyncResult<ItemResource>() {
            @Override
            public ItemResource invoke() {
                return itemRepository.getById(id);
            }
        };
    }
    
    private ItemResource getDefaultItem(String id) {
        LOGGER.warn("Failed to get item from catalog-item-service. Returning default item with id " + id);
        ItemResource item = new ItemResource();
        return item;
    }
}
