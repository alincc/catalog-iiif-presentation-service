package no.nb.microservices.iiifpresentation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.iiifpresentation.config.ApplicationSettings;
import no.nb.microservices.iiifpresentation.exception.RetrieveItemException;
import no.nb.microservices.iiifpresentation.model.LabelValue;
import no.nb.microservices.iiifpresentation.model.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andreasb on 11.06.15.
 */
@Service
public class ManifestService implements IManifestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestService.class);

    private final IItemService itemService;

    private final ApplicationSettings applicationSettings;
    
    @Autowired
    public ManifestService(IItemService itemService, ApplicationSettings applicationSettings) {
        this.itemService = itemService;
        this.applicationSettings = applicationSettings;
    }

    @Override
    public Manifest getManifest(String id, String idUri) {
        Manifest manifest = new Manifest();
        Future<ItemResource> itemFuture;
        ItemResource item;
        
        try {
            itemFuture = itemService.getItemByIdAsync(id);
            item = itemFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Error retrieving item '" + id + "'", e);
            throw new RetrieveItemException(e.getMessage());
        }

        // Context
        manifest.setContext(applicationSettings.getContextUrl());

        // Type
        manifest.setType("sc:Manifest");

        // ID
        manifest.setId(idUri);

        // Label
        manifest.setLabel((item.getMetadata() != null && item.getMetadata().getTitleInfo() != null) ? item.getMetadata().getTitleInfo().getTitle() : "Untitled");

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

        if(item.getMetadata() != null) {
        }
        return metadataList;
    }
}
