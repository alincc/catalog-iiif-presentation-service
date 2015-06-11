package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.iiifpresentation.model.LabelValue;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.reactor.LatchException;
import no.nb.microservices.iiifpresentation.reactor.MetadataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static reactor.event.selector.Selectors.$;

/**
 * Created by andreasb on 11.06.15.
 */
@Service
public class ManifestService {

    private final MetadataService metadataService;
    private final Reactor reactor;

    private Consumer<Event<MetadataWrapper>> metadataConsumer;

    @Autowired
    public ManifestService(MetadataService metadataService, Reactor reactor, Consumer<Event<MetadataWrapper>> metadataConsumer) {
        this.reactor = reactor;
        this.metadataService = metadataService;
        this.metadataConsumer = metadataConsumer;
    }

    @PostConstruct
    void init() {
        reactor.on($("metadata"), metadataConsumer);
    }

    /**
     * TODO: This method must do async calls to get metadata and structure information
     * @param id
     * @return
     */
    public Manifest getManifest(String id) {
        Mods mods = metadataService.getModsById(id);
        Manifest manifest = new Manifest();

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

    private Mods consumeMetadata(String id) {
        final CountDownLatch latch = new CountDownLatch(1);
        Mods mods = null;

        reactor.notify("metadata", Event.wrap(new MetadataWrapper(id, mods, latch)));

        waitForAllItemsToFinish(latch);

        return mods;
    }

    private void waitForAllItemsToFinish(final CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new LatchException(e);
        }
    }
}
