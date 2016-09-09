package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dwang on 9/7/2016.
 */
public class ImportUnitRequest {
    @JsonProperty("upload_id")
    public String uploadId;
    @JsonProperty("unit_key")
    public UnitKey unitKey;
    @JsonProperty("unit_type_id")
    public String unitTypeId;

    @JsonIgnore
    public static ImportUnitRequest getPuppetImportRequest(String owner, String name, String version, String uploadId){
        ImportUnitRequest req=new ImportUnitRequest();
        req.uploadId = uploadId;
        req.unitTypeId = "puppet_module";
        PuppetUnitKey unitKey = new PuppetUnitKey(name,version,owner);
        req.unitKey=unitKey;
        return req;
    }
    @JsonIgnore
    public static ImportUnitRequest getRPMImportRequest( String name, String version, String release, int epoch, String architecture , String uploadId){
        ImportUnitRequest req=new ImportUnitRequest();
        req.uploadId = uploadId;
        req.unitTypeId = "puppet_module";
        RPMUnitKey unitKey = new RPMUnitKey();
        unitKey.name = name;
        unitKey.version = version;
        unitKey.release = release;
        unitKey.epoch = epoch;
        unitKey.architecture = architecture;
        req.unitKey=unitKey;
        return req;
    }
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
