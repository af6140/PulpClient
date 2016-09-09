package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static ImportUnitRequest getPuppetImportRequest(String owner, String name, String version, String uploadId){
        ImportUnitRequest req=new ImportUnitRequest();
        req.uploadId = uploadId;
        req.unitTypeId = "puppet_module";
        PuppetUnitKey unitKey = new PuppetUnitKey(name,version,owner);
        req.unitKey=unitKey;
        return req;
    }
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
}
