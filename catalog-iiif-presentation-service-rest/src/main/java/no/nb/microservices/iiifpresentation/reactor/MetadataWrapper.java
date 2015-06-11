package no.nb.microservices.iiifpresentation.reactor;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;

import java.util.concurrent.CountDownLatch;

/**
 * Created by andreasb on 11.06.15.
 */
public class MetadataWrapper {
    private String id;
    private Mods mods;
    private CountDownLatch latch;

    public MetadataWrapper(String id, Mods mods, CountDownLatch latch) {
        this.id = id;
        this.mods = mods;
        this.latch = latch;
    }

    public String getId() {
        return id;
    }

    public Mods getMods() {
        return mods;
    }

    public void setMods(Mods mods) {
        this.mods = mods;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
