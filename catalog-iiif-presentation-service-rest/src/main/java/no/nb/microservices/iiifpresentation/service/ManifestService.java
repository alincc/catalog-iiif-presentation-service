package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.model.LabelValue;
import no.nb.microservices.iiifpresentation.model.Manifest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by andreasb on 11.06.15.
 */
@Service
public class ManifestService {

    private final ItemService itemService;

    @Autowired
    public ManifestService(ItemService itemService) {
        this.itemService = itemService;
    }

    public Manifest getManifest(String id) throws InterruptedException, ExecutionException {
        Manifest manifest = new Manifest();
        Future<ItemResource> itemFuture = itemService.getItemByIdAsync(id);

        ItemResource item = itemFuture.get();

        // Context
        manifest.setContext("");

        // Type
        manifest.setType("");

        // ID
        manifest.setId("");

        // Label
        manifest.setLabel((item.getMetadata().getTitleInfo() != null) ? item.getMetadata().getTitleInfo().getTitle() : "Untitled");

        // Metadata
        manifest.setMetadata(buildMetadataList(item));

        // Description
        // TODO: Build a longer description of the object
        manifest.setDescription("");

        // License
        manifest.setLicense("");

        // Attribution
        manifest.setAttribution("");

        // Service
        manifest.setService(null);

        // See also
        manifest.setSeeAlso(null);

        // Within
        manifest.setWithin("");

        // Sequences

        // Structures

        return manifest;
    }

    private List<LabelValue> buildMetadataList(ItemResource item) {
        List<LabelValue> metadataList = new ArrayList<>();

        //metadataList.add(new LabelValue("Publisher", (item.getMetadata().getPublisher());

        return metadataList;
    }
}
