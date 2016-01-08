package no.nb.microservices.iiifpresentation.core.manifest;

public interface ManifestService {
    ItemStructPair getItemAndStruct(String manifestId);
}
