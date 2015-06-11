package no.nb.microservices.iiifpresentation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.iiifpresentation.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andreasb on 11.06.15.
 */
@Service
public class MetadataService {
    MetadataRepository metadataRepository;

    @Autowired
    public MetadataService(MetadataRepository metadataRepository) {
        super();
        this.metadataRepository = metadataRepository;
    }

    @HystrixCommand(fallbackMethod = "getDefaultMods")
    public Mods getModsById(String id) {
        return metadataRepository.getModsById(id);
    }

    private Mods getDefaultMods(String id) {
        return new Mods();
    }
}
