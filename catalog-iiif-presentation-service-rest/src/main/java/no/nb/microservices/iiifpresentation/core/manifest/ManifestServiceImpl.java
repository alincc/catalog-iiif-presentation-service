package no.nb.microservices.iiifpresentation.core.manifest;

import javax.servlet.http.HttpServletRequest;

import org.apache.htrace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import no.nb.commons.web.util.UserUtils;
import no.nb.commons.web.xforwarded.feign.XForwardedFeignInterceptor;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;
import no.nb.microservices.iiifpresentation.core.item.ItemRepository;
import no.nb.microservices.iiifpresentation.core.metadata.repository.MetadataRepository;
import no.nb.microservices.iiifpresentation.exception.ItemNotFoundException;
import reactor.Environment;
import reactor.fn.Function;
import reactor.fn.tuple.Tuple2;
import reactor.rx.Stream;
import reactor.rx.Streams;

@Service
public class ManifestServiceImpl implements ManifestService {

    private final ItemRepository itemRepository;
    private final MetadataRepository metadataRepository;

    @Autowired
    public ManifestServiceImpl(ItemRepository itemRepository, MetadataRepository metadataRepository) {
        super();
        this.itemRepository = itemRepository;
        this.metadataRepository = metadataRepository;
    }

    @Override
    public ItemStructPair getManifest(String id) {
        try {
            SecurityInfo securityInfo = getSecurityInfo();

            TracableId tracableId = new TracableId(Trace.currentSpan(), id, securityInfo);
            Stream<ItemStructPair> resp = Streams.zip(getItemById(tracableId), getStructById(tracableId),
                (Tuple2<ItemResource, StructMap> tup) -> {
                    ItemResource item = tup.getT1();
                    StructMap struct = tup.getT2();
                    return new ItemStructPair(item, struct);                    
                });

            return resp.next().await();
        } catch (Exception ex) {
            throw new ItemNotFoundException("Failed getting metadata for id " + id, ex);
        }

    }
    
    public Stream<ItemResource> getItemById(TracableId id) {
        Environment env = new Environment();
        return Streams.just(id).dispatchOn(env)
                .map(new Function<TracableId, ItemResource>() {
                    @Override
                    public ItemResource apply(TracableId id) {
                        Trace.continueSpan(id.getSpan());
                        SecurityInfo securityInfo = id.getSecurityInfo();
                        return itemRepository.getById(id.getId(), securityInfo.getxHost(), securityInfo.getxPort(), securityInfo.getxRealIp(), securityInfo.getSsoToken());
                    }
                });
    }    
    
    public Stream<StructMap> getStructById(TracableId id) {
        Environment env = new Environment();
        return Streams.just(id).dispatchOn(env)
                .map(new Function<TracableId, StructMap>() {
                    @Override
                    public StructMap apply(TracableId id) {
                        Trace.continueSpan(id.getSpan());
                        SecurityInfo securityInfo = id.getSecurityInfo();
                        return metadataRepository.getStructById(id.getId(), securityInfo.getxHost(), securityInfo.getxPort(), securityInfo.getxRealIp(), securityInfo.getSsoToken());
                    }
                });
    }    

    private SecurityInfo getSecurityInfo() {
        SecurityInfo securityInfo = new SecurityInfo();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        securityInfo.setxHost(request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_HOST));
        securityInfo.setxPort(request.getHeader(XForwardedFeignInterceptor.X_FORWARDED_PORT));
        securityInfo.setxRealIp(UserUtils.getClientIp(request));
        securityInfo.setSsoToken(UserUtils.getSsoToken(request));
        return securityInfo;
    }

}
