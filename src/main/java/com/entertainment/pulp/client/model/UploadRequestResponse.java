package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Created by dwang on 9/2/2016.
 */
public class UploadRequestResponse {

    @JsonProperty("upload_id")
    public String uploadId;

    @JsonProperty("_href")
    public String href;

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String result =null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
