package no.nb.microservices.iiifpresentation.core.manifest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.core.item.IItemService;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;

@Service
public class ManifestServiceImpl implements ManifestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestServiceImpl.class);

    private final IItemService itemService;

    @Autowired
    public ManifestServiceImpl(IItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ItemStructPair getManifest(String id) {
        ItemResource item;
        
        try {
            Future<ItemResource> itemFuture = itemService.getItemByIdAsync(id);
            item = itemFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error retrieving item '" + id + "'", e);
            throw new RetrieveItemException(e.getMessage());
        }
        
        return new ItemStructPair(item, null);

    }

}
