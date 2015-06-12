package no.nb.microservices.iiifpresentation.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.iiifpresentation.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    @HystrixCommand(fallbackMethod = "getDefaultMods")
    public Future<Mods> getModsById(String id) throws InterruptedException {
        Mods mods = metadataRepository.getModsById(id);
        return new AsyncResult<Mods>(mods);
    }

    private Future<Mods> getDefaultMods(String id) throws InterruptedException {
        return new AsyncResult<Mods>(new Mods());
    }
}
