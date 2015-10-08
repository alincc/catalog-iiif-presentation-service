package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.test.struct.TestStructMap;
import no.nb.microservices.iiifpresentation.model.Sequence;

public class SequenceFixtures {

    public final Sequence SEQUENCE = new SequenceBuilder()
            .withId("id1")
            .withStruct(TestStructMap.aDefaultStructMap().build()).build();

}
