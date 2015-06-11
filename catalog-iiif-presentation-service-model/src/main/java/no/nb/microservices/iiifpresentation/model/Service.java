package no.nb.microservices.iiifpresentation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andreasb on 11.06.15.
 */
public class Service {
    @JsonProperty("@context")
    private String context;

    @JsonProperty("@id")
    private String id;

    private String profile;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
