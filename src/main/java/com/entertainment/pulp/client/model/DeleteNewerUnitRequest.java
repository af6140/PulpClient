package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dwang on 9/8/2016.
 */
public class DeleteNewerUnitRequest {
    @JsonProperty("criteria")
    public JsonNode criteria;

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
