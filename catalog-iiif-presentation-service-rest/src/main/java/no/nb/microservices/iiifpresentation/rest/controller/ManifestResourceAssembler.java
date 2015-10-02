package no.nb.microservices.iiifpresentation.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;

import no.nb.microservices.iiifpresentation.core.manifest.ItemStructPair;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.rest.controller.assembler.ManifestBuilder;

public class ManifestResourceAssembler {

    public Manifest toResource(ItemStructPair entity) {
        Link selfRel = linkTo(methodOn(ManifestController.class).getManifest("id1", null)).withSelfRel();
        return new ManifestBuilder(selfRel.getHref())
                .withItem(entity.getItem())
                .withStruct(entity.getStruct())
                .build();
    }

}
