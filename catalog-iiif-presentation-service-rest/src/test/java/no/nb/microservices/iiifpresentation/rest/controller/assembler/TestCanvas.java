package no.nb.microservices.iiifpresentation.rest.controller.assembler;

import no.nb.microservices.catalogmetadata.model.struct.Div;
import no.nb.microservices.catalogmetadata.model.struct.Hotspot;
import no.nb.microservices.catalogmetadata.model.struct.Hs;
import no.nb.microservices.catalogmetadata.model.struct.Resource;

public final class TestCanvas {
    
    public static CanvasBuilder aDefaultCanvas() {
        Div div = createDefaultDiv();

        return new CanvasBuilder()
                .withManifestId("id1")
                .withDiv(div);
    }

    public static CanvasBuilder aCanvasWithHotspots() {
        Div div = createDefaultDiv();

        Hotspot hotspot = new Hotspot();
        hotspot.setB(1000);
        hotspot.setL(2000);
        hotspot.setR(3000);
        hotspot.setT(4000);
        hotspot.setHszId("1_2_3");
        Hs hs = new Hs();
        hs.setHsId("URN");
        hs.setValue("Summary");
        hotspot.setHs(hs);
        div.getHotspots().add(hotspot);
        
        return new CanvasBuilder()
                .withManifestId("id1")
                .withDiv(div);
    }

    private static Div createDefaultDiv() {
        Div div = new Div("id1");
        div.setOrder("1");
        div.setOrderLabel("p1");
        div.setType("PAGE");
        Resource resource = new Resource();
        resource.setWidth(1);
        resource.setHeight(2);
        resource.setHref("URN:NBN:no-nb_digibok_2001010100001_123");
        div.setResource(resource);
        return div;
    }


}
