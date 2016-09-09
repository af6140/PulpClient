package com.entertainment.pulp.client;

import org.springframework.web.client.RestTemplate;

/**
 * Created by dwang on 9/9/2016.
 */
public class BasicManager {

    private RestTemplate restTemplate;
    private  PulpClientConfiguration pulpClientConfiguration;

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PulpClientConfiguration getPulpClientConfiguration() {
        return pulpClientConfiguration;
    }

    public void setPulpClientConfiguration(PulpClientConfiguration pulpClientConfiguration) {
        this.pulpClientConfiguration = pulpClientConfiguration;
    }
}
