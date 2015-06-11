package no.nb.microservices.iiifpresentation.repository;

import no.nb.microservices.catalogmetadata.model.fields.Field;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by andreasb on 11.06.15.
 */
@FeignClient("catalog-metadata-service")
public interface MetadataRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/mods", produces = MediaType.APPLICATION_XML_VALUE)
    Mods getModsById(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Field> getFieldsById(@PathVariable("id") String id);

}
