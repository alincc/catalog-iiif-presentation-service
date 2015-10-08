package no.nb.microservices.iiifpresentation.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NullContext implements Context {

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> headerValues = new LinkedList<String>();
        headerValues.add("application/ld+json;charset=UTF-8");
        headers.put("Content-Type", headerValues);
        return headers;
    }

}
