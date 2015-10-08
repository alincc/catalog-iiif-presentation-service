package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.hateoas.Link;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogitem.rest.model.Person;
import no.nb.microservices.catalogitem.rest.model.Role;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.model.Context;
import no.nb.microservices.iiifpresentation.model.LabelValue;
import no.nb.microservices.iiifpresentation.model.Manifest;
import no.nb.microservices.iiifpresentation.model.NullContext;
import no.nb.microservices.iiifpresentation.model.Sequence;
import no.nb.microservices.iiifpresentation.rest.controller.ManifestController;

public class ManifestBuilder {

    private Context context;
    private final String id;
    private StructMap struct;
    private ItemResource item;
    
    public ManifestBuilder(String id) {
        super();
        this.context = new NullContext();
        this.id = id;
    }
    
    public ManifestBuilder withContext(
            Context context) {
        this.context = context;
        return this;
    }
    
    public ManifestBuilder withStruct(StructMap struct) {
        this.struct = struct;
        return this;
    }

    public ManifestBuilder withItem(ItemResource item) {
        this.item = item;
        return this;
    }
    
    public Manifest build() {
        
        Manifest manifest = new Manifest(context);
        manifest.setType("sc:Manifest");
        Link selfRel = linkTo(methodOn(ManifestController.class).getManifest(id, null)).withSelfRel();
        manifest.setId(selfRel.getHref());
        manifest.setLabel((item != null && item.getMetadata() != null && item.getMetadata().getCompositeTitle() != null) ? item.getMetadata().getCompositeTitle() : "Untitled");
        if (item != null) {
            Metadata metadata = item.getMetadata();
            if (metadata != null) {
                manifest.setMetadata(buildMetadataList(item));
                manifest.setDescription(metadata.getSummary());
            }
        }
        manifest.setLicense("");
        manifest.setAttribution("");
        manifest.setService(null);
        manifest.setSeeAlso(null);
        manifest.setWithin("");
        manifest.setSequences(Arrays.asList(createNormalSequence()));

        return manifest;
    }

    private Sequence createNormalSequence() {
        return new SequenceBuilder().withManifestId(id).withStruct(struct).build();
    }
    
    private List<LabelValue> buildMetadataList(ItemResource item) {
        List<LabelValue> metadataList = new ArrayList<>();

        if (item != null) {
            Metadata metadata = item.getMetadata();
            if(metadata != null) {
                addAuthorToMetadataList(metadata, metadataList);
            }
        }
        return metadataList;
    }
    
    private void addAuthorToMetadataList(Metadata metadata, List<LabelValue> metadataList) {
        List<Person> people = metadata.getPeople();
        if(people == null) {
            return;
        }
        for(Person person : people) {
            if(isAuthor(person)) {
                metadataList.add(new LabelValue("Author", person.getName()));
            }
        }
    }
    
    private boolean isAuthor(Person person) {
        List<Role> roles = person.getRoles();
        if(roles == null) {
            return false;
        }
        
        for(Role role : person.getRoles()) {
            if("Creator".equalsIgnoreCase(role.getName())) {
                return true;
            }
        }
        return false;
    }

}
