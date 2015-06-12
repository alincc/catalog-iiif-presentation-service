package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
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

    private final MetadataService metadataService;

    @Autowired
    public ManifestService(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    public Manifest getManifest(String id) throws InterruptedException, ExecutionException {
        Manifest manifest = new Manifest();
        Future<Mods> modsFuture = metadataService.getModsById(id);
        Mods mods = modsFuture.get();

        // Label
        manifest.setLabel((mods.getTitleInfos().size() > 0) ? mods.getTitleInfos().get(0).getTitle() : "No title");

        // Description
        // TODO: Build a longer description of the object
        manifest.setDescription("");

        // Metadata
        manifest.setMetadata(buildMetadataList(mods));

        // License
        // TODO: Fix
        manifest.setLicense("");

        // Attribution
        // TODO: Fix
        manifest.setAttribution("");

        return manifest;
    }

    private List<LabelValue> buildMetadataList(Mods mods) {
        List<LabelValue> metadataList = new ArrayList<>();

        return metadataList;
    }
}
