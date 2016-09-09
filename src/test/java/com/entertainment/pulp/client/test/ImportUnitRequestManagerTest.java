package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.ImportUnitRequestManager;
import com.entertainment.pulp.client.model.ImportUnitRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

/**
 * Created by dwang on 9/9/2016.
 */
public class ImportUnitRequestManagerTest extends BaseManagerTest {
    private ImportUnitRequestManager importUnitRequestManager;

    public ImportUnitRequestManager getImportUnitRequestManager() {
        return importUnitRequestManager;
    }

    public void setImportUnitRequestManager(ImportUnitRequestManager importUnitRequestManager) {
        this.importUnitRequestManager = importUnitRequestManager;
    }

    @Before
    public void setUp() {
        super.setUp();
        this.importUnitRequestManager = new ImportUnitRequestManager();
        this.importUnitRequestManager.setPulpClientConfiguration(this.getClientConfiguration());
        this.importUnitRequestManager.setRestTemplate(this.getRestTemplate());
    }

    @Test
    public void testImportPuppetModule() {
        String owner = "af6140";
        String name ="testdummy";
        String version="0.1.0";
        String repoId = "dest";
        String uploadId = "12345";
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.importUnitRequestManager.getServicePath().replace("{repo_id}", "dest"));
        this.getMockRestServiceServer().expect(requestMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.importUnitRequestManager.importPuppetModule(owner,name,version,uploadId, repoId);
    }

    @Test
    public void testImportRPM() {
        String name ="testdummy";
        String version="0.1.0";
        String release ="1a";
        int epoch = 0;
        String architecture = "noarch";
        String repoId = "dest";
        String uploadId = "12345";
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.importUnitRequestManager.getServicePath().replace("{repo_id}", "dest"));
        this.getMockRestServiceServer().expect(requestMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.importUnitRequestManager.importRPM(name,version, release,epoch,architecture,uploadId,repoId);
    }
}
