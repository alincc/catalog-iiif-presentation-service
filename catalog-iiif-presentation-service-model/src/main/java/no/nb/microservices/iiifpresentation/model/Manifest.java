package no.nb.microservices.iiifpresentation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "context", "type", "id", "label", "metadata", "description", "license", "attribution", "service", "seeAlso", "within" })
public class Manifest {
    @JsonProperty("@context")
    private String context;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@id")
    private String id;

    private String label;
    private List<LabelValue> metadata;
    private String description;
    private String license;
    private String attribution;
    private Service service;
    private SeeAlso seeAlso;
    private String within;
    private List<Sequence> sequences;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<LabelValue> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<LabelValue> metadata) {
        this.metadata = metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public SeeAlso getSeeAlso() {
        return seeAlso;
    }

    public void setSeeAlso(SeeAlso seeAlso) {
        this.seeAlso = seeAlso;
    }

    public String getWithin() {
        return within;
    }

    public void setWithin(String within) {
        this.within = within;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

}
