package no.nb.microservices.iiifpresentation.service;

import java.util.concurrent.Future;

import no.nb.microservices.catalogitem.rest.model.ItemResource;

public interface IItemService {

    Future<ItemResource> getItemByIdAsync(String id) throws InterruptedException;
}
