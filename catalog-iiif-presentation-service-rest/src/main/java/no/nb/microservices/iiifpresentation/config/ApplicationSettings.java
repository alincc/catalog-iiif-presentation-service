package no.nb.microservices.iiifpresentation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "microservice")
public class ApplicationSettings {

    private String contextUrl;

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }
    
    public String getContextUrl() {
        return this.contextUrl;
    }
}
