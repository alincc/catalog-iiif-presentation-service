package no.nb.microservices.iiifpresentation.core.manifest;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;

public class ItemStructPair {

    private final ItemResource item;
    private final StructMap struct;
    
    public ItemStructPair(ItemResource item, StructMap struct) {
        super();
        this.item = item;
        this.struct = struct;
    }

    public ItemResource getItem() {
        return item;
    }

    public StructMap getStruct() {
        return struct;
    }
    
}
