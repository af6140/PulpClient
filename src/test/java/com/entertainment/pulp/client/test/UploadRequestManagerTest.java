package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.UploadRequestManager;
import com.entertainment.pulp.client.model.UploadRequestResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.io.File;
import java.io.IOException;

/**
 * Created by dwang on 9/9/2016.
 */
public class UploadRequestManagerTest extends BaseManagerTest {
    private UploadRequestManager uploadRequestManager;

    @Before
    public void setUp(){
        super.setUp();
        this.uploadRequestManager =new UploadRequestManager();
        this.uploadRequestManager.setPulpClientConfiguration(this.getClientConfiguration());
        this.uploadRequestManager.setRestTemplate(this.getRestTemplate());
    }

    @Test
    public void testGetUploadRequest() {
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath());
        this.getMockRestServiceServer().expect(requestMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.uploadRequestManager.getUploadRequest();
        this.getMockRestServiceServer().verify();
    }

    @Test
    public void testDeleteUploadRequest() {
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath()+"12345/");
        this.getMockRestServiceServer().expect(requestMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.uploadRequestManager.deleteUploadRequest("12345");
        this.getMockRestServiceServer().verify();
    }

    @Test
    public void testUploadBits() {
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath()+"12345/1025/");
        RequestMatcher contentMatcher = MockRestRequestMatchers.content().bytes("abcdefg".getBytes());
        this.getMockRestServiceServer().expect(requestMatcher).andExpect(contentMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.uploadRequestManager.uploadBits("12345", 1025, "abcdefg".getBytes());
        this.getMockRestServiceServer().verify();
    }

    @Test
    public void testUploadFile() throws IOException {
        String file  = "src/test/resources/ugquickstart.txt";
        int chunkSize =4096;
        File f = new File(file);
        long fileSize = f.length();
        long uploadTime = fileSize/chunkSize +1 ;
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath());
        UploadRequestResponse uploadRequestResponse = new UploadRequestResponse();
        uploadRequestResponse.uploadId = "12345";
        uploadRequestResponse.href = "http:/wwww.test.com/etc";
        this.getMockRestServiceServer().expect(requestMatcher).andRespond(MockRestResponseCreators.withSuccess(uploadRequestResponse.toString(), MediaType.APPLICATION_JSON));
        for (int i =0 ; i < uploadTime; i++) {
            int offset = i* chunkSize;
            RequestMatcher requestMatcher1= MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath()+"12345/"+offset+"/");
            this.getMockRestServiceServer().expect(requestMatcher1).andRespond(MockRestResponseCreators.withSuccess());
        }
        RequestMatcher requestMatcher3= MockRestRequestMatchers.requestTo(this.uploadRequestManager.getServicePath()+"12345/");
        this.getMockRestServiceServer().expect(requestMatcher3).andRespond(MockRestResponseCreators.withSuccess());
        this.uploadRequestManager.uploadFile(file, chunkSize);
        this.getMockRestServiceServer().verify();
    }
}
