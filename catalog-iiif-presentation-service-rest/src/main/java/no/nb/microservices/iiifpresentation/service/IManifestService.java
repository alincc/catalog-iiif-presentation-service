package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.iiifpresentation.model.Manifest;

public interface IManifestService {
    Manifest getManifest(String id, String idUri);
}
