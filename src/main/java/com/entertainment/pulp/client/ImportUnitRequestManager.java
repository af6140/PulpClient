package com.entertainment.pulp.client;

import com.entertainment.pulp.client.model.ImportUnitRequest;
import com.entertainment.pulp.client.model.UploadRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dwang on 9/7/2016.
 */
public class ImportUnitRequestManager extends  BasicManager{

    public static String SERVICE_PATH = "/api/v2/repositories/{repo_id}/actions/import_upload/";

    public String getServicePath() {
        return this.getPulpClientConfiguration().getServerUrl()+SERVICE_PATH;
    }

    public boolean importPuppetModule(String owner, String name, String version, String uploadId,  String repoId){
        ImportUnitRequest importUnitRequest = ImportUnitRequest.getPuppetImportRequest(owner, name, version, uploadId);
        return this.importUnit(importUnitRequest, repoId);
    }

    public boolean importRPM( String name, String version,String release, int epoch, String architecture , String uploadId,  String repoId){
        ImportUnitRequest importUnitRequest = ImportUnitRequest.getRPMImportRequest(name, version, release, epoch, architecture, uploadId);
        return this.importUnit(importUnitRequest, repoId);
    }

    private boolean importUnit(ImportUnitRequest req, String repoId) {
        RestTemplate restTemplate = this.getRestTemplate();
        Map<String,String> urlVariables = new HashMap<String,String>();
        urlVariables.put("repo_id", repoId);
        //restTemplate.postForLocation(SERVICE_PATH,importUnitRequest, urlVariables);
        System.out.println("req: "+ req.toString());
        ResponseEntity<String> response = restTemplate.postForEntity(this.getServicePath(),req ,String.class, urlVariables);
        HttpStatus status_code = response.getStatusCode();
        if(status_code.is2xxSuccessful()){
            return true;
        }else {
            return false;
        }
    }
}
