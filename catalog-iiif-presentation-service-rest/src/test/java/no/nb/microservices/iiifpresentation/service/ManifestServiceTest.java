package no.nb.microservices.iiifpresentation.service;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogmetadata.model.mods.v3.TitleInfo;
import no.nb.microservices.iiifpresentation.Application;
import no.nb.microservices.iiifpresentation.model.Manifest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by andreasb on 15.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ManifestServiceTest {

    @Mock
    private MetadataService metadataService;

    @InjectMocks
    private ManifestService manifestService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getManifestTest() throws Exception {
        // Setup
        String id = "1234";
        Future<Mods> mods = new Future<Mods>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Mods get() throws InterruptedException, ExecutionException {
                Mods mods = new Mods();
                mods.setTitleInfos(new ArrayList<>());
                TitleInfo titleInfo = new TitleInfo();
                titleInfo.setTitle("Donald Ducks great adventure");
                mods.getTitleInfos().add(titleInfo);
                return mods;
            }

            @Override
            public Mods get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };

        // Mocks
        when(metadataService.getModsByIdAsync(id)).thenReturn(mods);

        // Call
        Manifest manifest = manifestService.getManifest(id);

        // Asserts
        assertEquals("Donald Ducks great adventure", manifest.getLabel());
        verify(metadataService, times(1)).getModsByIdAsync(id);

    }
}
