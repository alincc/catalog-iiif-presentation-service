package no.nb.microservices.iiifpresentation.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "width","height"})
public class Size {
    private int width;
    private int height;

    @JsonCreator
    public Size() {
        super();
    }
    
    public Size(int width, int height) {
        this();
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    @Override
    public int hashCode(){
        return new HashCodeBuilder()
            .append(width)
            .append(height)
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof Size){
            final Size other = (Size) obj;
            return new EqualsBuilder()
                .append(width, other.width)
                .append(height, other.height)
                .isEquals();
        } else{
            return false;
        }
    }    
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("width", width).
                append("height", height).
                toString();
    }
    
}
