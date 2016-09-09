package com.entertainment.pulp.client;

import com.entertainment.pulp.client.model.CopyUnitRequest;
import com.entertainment.pulp.client.model.DeleteNewerUnitRequest;
import com.entertainment.pulp.client.model.PuppetUnitKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dwang on 9/7/2016.
 */
public class UnitAssociationManager extends  BasicManager{

    public static String SERVICE_PATH = "/pulp/api/v2/repositories/{repo_id}/actions/associate/";
    public static String DELETE_SERVICE_PATH = "/pulp/api/v2/repositories/{repo_id}/actions/unassociate/";


    public String getServicePath(){
        return this.getPulpClientConfiguration().getServerUrl() + SERVICE_PATH;
    }

    public String getDeleteServicePath() {
        return this.getPulpClientConfiguration().getServerUrl() + DELETE_SERVICE_PATH;
    }

    public boolean copyPuppetModule(String source_repo, String dest_repo, String owner, String name, String version){
        //this.getRestTemplate().postForEn
        CopyUnitRequest copyUnitRequest = new CopyUnitRequest();
        copyUnitRequest.sourceRepo = source_repo;
        PuppetUnitKey unitKey = new PuppetUnitKey(name, version, owner);
        copyUnitRequest.criteria = unitKey.getAssociationCriteriaJsonNode();
        Map<String,String> urlVariables = new HashMap<String,String>();
        urlVariables.put("repo_id", dest_repo);
        System.out.println(copyUnitRequest.toString());
        ResponseEntity<String> response = this.getRestTemplate().postForEntity(this.getServicePath(), copyUnitRequest, String.class, urlVariables);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return false;
        }
        return true;
    }
    public boolean deletNewerPuppetModule(String repoId, String owner, String name, String version) {
        boolean success = false;
        DeleteNewerUnitRequest deleteNewerUnitRequest = new DeleteNewerUnitRequest();
        PuppetUnitKey unitKey = new PuppetUnitKey(name, version, owner);
        deleteNewerUnitRequest.criteria = unitKey.getUnAssociateNewerCriteriaJsonNode();
        System.out.println(deleteNewerUnitRequest.toString());
        Map<String,String> urlVariables = new HashMap<String,String>();
        urlVariables.put("repo_id", repoId);
        ResponseEntity<String> responseEntity=this.getRestTemplate().postForEntity(this.getDeleteServicePath(), deleteNewerUnitRequest, String.class, urlVariables);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            success = true;
        }
        return success;
    }
}
