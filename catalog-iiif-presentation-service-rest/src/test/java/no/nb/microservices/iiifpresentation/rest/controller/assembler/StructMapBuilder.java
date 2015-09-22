package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.Resource;
import no.nb.microservices.catalogmetadata.model.struct.StructMap;

public class StructMapBuilder {

    private int numberOfpages = 10;
    private final DecimalFormat df = new DecimalFormat("0000");
    
    public StructMapBuilder withPages(int numberOfPages) {
        this.numberOfpages = numberOfPages;
        return this;
    }
    
    public StructMap build() {
        List<Div> divs = new ArrayList<>();
        for(int pageNumber = 0; pageNumber < numberOfpages; pageNumber++) {
            divs.add(createDiv(pageNumber));
        }
        StructMap struct = new StructMap();
        struct.setDivs(divs);
        
        return struct;
    }

    private Div createDiv(int pageNumber) {
        Div div = new Div();
        Resource resource = new Resource();
       
        try {
            resource.setHref("URN:NBN:no-nb_digibok_2001010100001_" + df.parse(""+pageNumber));
        } catch (ParseException e) {
        }
        div.setResource(resource);
        return div;
    }


}
