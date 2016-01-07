package no.nb.microservices.iiifpresentation.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "context", "id", "type", "resources" })
public class AnnotationList {
    @JsonProperty("@context")
    private String context;
    @JsonProperty("@id")
    private String id;
    @JsonProperty("@type")
    private String type;
    private List<Annotation> resources;

    public AnnotationList() {
        super();
        this.context = new NullContext().getContext();
        this.type = "sc:AnnotationList";
    }
    
    public String getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context.getContext();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Annotation> getResources() {
        return resources;
    }

    public void setResources(List<Annotation> resources) {
        this.resources = resources;
    }
    
    @JsonIgnore
    public void addResource(Annotation annotation) {
        if (resources == null) {
            resources = new ArrayList<>();
        }
        resources.add(annotation);
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(context).
            append(id).
            append(type).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { 
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
          return false;
        }
        AnnotationList rhs = (AnnotationList) obj;
        return new EqualsBuilder().
            append(context, rhs.context).
            append(id, rhs.id).
            append(type, rhs.type).
            isEquals();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
          append("context", context).
          append("id", id).
          append("type", type).
          toString();
    }
    
}
