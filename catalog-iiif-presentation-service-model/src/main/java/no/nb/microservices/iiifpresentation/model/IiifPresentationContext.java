package no.nb.microservices.iiifpresentation.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IiifPresentationContext implements Context {

    @Override
    public String getContext() {
        return "http://iiif.io/api/presentation/2/context.json";
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Link", Arrays.asList("<http://iiif.io/api/presentation/2/context.json>;rel=\"http://www.w3.org/ns/json-ld#context\";type=\"application/ld+json\""));
        headers.put("Content-Type", Arrays.asList("application/json;charset=UTF-8"));
        return headers;
    }

}
