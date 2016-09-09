package com.entertainment.pulp.client;


import com.entertainment.pulp.client.model.UploadRequestResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dwang on 9/2/2016.
 */
public class UploadRequestManager extends  BasicManager{

    public static String SERVICE_PATH = "/api/v2/content/uploads/";

    public String getServicePath() {
        return this.getPulpClientConfiguration().getServerUrl()+SERVICE_PATH;
    }
    // return upload request id
    public UploadRequestResponse getUploadRequest() {
        RestTemplate restTemplate=this.getRestTemplate();
        UploadRequestResponse uploadRequest = restTemplate.postForObject(this.getServicePath(), null, UploadRequestResponse.class);
        return uploadRequest;
    }

    public boolean deleteUploadRequest(String uploadId) {
        boolean success=false;
        String deleteURL = this.getServicePath()+ "{upload_id}/";
        Map<String, String> urlVariables = new HashMap<String,String>();
        urlVariables.put("upload_id", uploadId);
        this.getRestTemplate().delete(deleteURL, urlVariables);
        return success;
    }

    public void uploadBits(String uploadId, int offset, byte[] bits) {
        RestTemplate restTemplate=this.getRestTemplate();
        String url = this.getServicePath()+"{upload_id}/{offset}/";
        Map<String, String> urlVariables;
        urlVariables =new HashMap<String,String>();
        urlVariables.put("upload_id", uploadId);
        urlVariables.put("offset", ""+offset);
        restTemplate.put(url,bits, urlVariables);
    }

    public void uploadFile(String filePath, int chunkSize) throws IOException{
        File file= new File(filePath);
        byte [] chunk = new byte[chunkSize];
        FileInputStream inputStream= FileUtils.openInputStream(file);
        UploadRequestResponse uploadRequestResponse = getUploadRequest();
        String uploadId = uploadRequestResponse.uploadId;
        int offset =0;
        int bytesRead =0;
        while ((bytesRead = inputStream.read(chunk))!= -1) {
            if (bytesRead>0 ) {
                uploadBits(uploadRequestResponse.uploadId, offset, chunk);
                offset = offset + bytesRead;
            }
        }
        this.deleteUploadRequest(uploadRequestResponse.uploadId);
    }
    public void  uploadFile(String filePath) throws IOException {
        this.uploadFile(filePath, 4096);
    }
}