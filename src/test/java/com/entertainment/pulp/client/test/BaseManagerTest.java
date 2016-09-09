package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.PulpClientConfiguration;
import com.entertainment.pulp.client.UnitAssociationManager;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dwang on 9/9/2016.
 */
@Ignore
public abstract class BaseManagerTest extends TestCase {

    private MockRestServiceServer mockRestServiceServer;
    private RestTemplate restTemplate;
    private PulpClientConfiguration clientConfiguration;

    public MockRestServiceServer getMockRestServiceServer() {
        return mockRestServiceServer;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public PulpClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    @Before
    protected void setUp() {
        clientConfiguration = new PulpClientConfiguration();
        clientConfiguration.setServerUrl("http://www.test.com/pulp");
        clientConfiguration.setUserName("admin");
        clientConfiguration.setPassword("admin");
        this.restTemplate = new RestTemplate();
        this.mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }
}
