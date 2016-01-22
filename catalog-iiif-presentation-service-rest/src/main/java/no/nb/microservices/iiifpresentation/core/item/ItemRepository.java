package no.nb.microservices.iiifpresentation.core.item;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("catalog-item-service")
public interface ItemRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/catalog/v1/items/{id}")
    ItemResource getById(@PathVariable("id") String id, 
            @RequestParam("X-Forwarded-Host") String xHost, 
            @RequestParam("X-Forwarded-Port") String xPort, 
            @RequestParam("X-Original-IP-Fra-Frontend") String xRealIp, 
            @RequestParam("amsso") String ssoToken);

}
