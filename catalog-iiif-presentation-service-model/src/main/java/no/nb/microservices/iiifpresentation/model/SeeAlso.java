package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andreasb on 11.06.15.
 */
public class SeeAlso {
    @JsonProperty("@id")
    private String id;

    private String format;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
