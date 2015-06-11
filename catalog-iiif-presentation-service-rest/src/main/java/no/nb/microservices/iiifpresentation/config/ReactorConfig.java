package no.nb.microservices.iiifpresentation.config;

import org.springframework.context.annotation.Bean;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

/**
 * Created by andreasb on 11.06.15.
 */
public class ReactorConfig {
    @Bean
    Reactor createReactor(Environment env) {
        return Reactors.reactor().env(env).dispatcher(Environment.THREAD_POOL).get();
    }
}
