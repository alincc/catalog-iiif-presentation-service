package no.nb.microservices.iiifpresentation.model;

import java.util.List;
import java.util.Map;

public interface Context {

    String getContext();

    Map<String, List<String>> getHeaders();
    
}
