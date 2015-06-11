package no.nb.microservices.iiifpresentation.reactor;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.iiifpresentation.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.event.Event;
import reactor.function.Consumer;

import java.lang.annotation.Annotation;

/**
 * Created by andreasb on 11.06.15.
 */
@Service
public class MetadataConsumer implements Consumer<Event<MetadataWrapper>> {

    private final MetadataService metadataService;

    @Autowired
    public MetadataConsumer(MetadataService metadataService) {
        super();
        this.metadataService = metadataService;
    }

    @Override
    public void accept(Event<MetadataWrapper> metadataWrapperEvent) {
        try {
            Mods mods = metadataService.getModsById(metadataWrapperEvent.getData().getId());
            metadataWrapperEvent.getData().setMods(mods);
        }
        finally {
            metadataWrapperEvent.getData().getLatch().countDown();
        }
    }
}
