package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;

public final class TestSequence {

    public static SequenceBuilder aDefaultSequence() {
        return new SequenceBuilder()
                .withManifestId("id1")
                .withStruct(TestStructMap.aDefaultStructMap().build());

    }
    
}
