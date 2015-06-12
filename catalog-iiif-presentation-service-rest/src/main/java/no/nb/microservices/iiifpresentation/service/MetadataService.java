package no.nb.microservices.iiifpresentation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.iiifpresentation.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

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

    @CacheResult
    @HystrixCommand(fallbackMethod = "getDefaultMods")
    public Future<Mods> getModsByIdAsync(String id) throws InterruptedException {
        return new AsyncResult<Mods>(metadataRepository.getModsById(id));
    }

    private Mods getDefaultMods(String id) {
        Mods mods = new Mods();
        return mods;
    }

}
