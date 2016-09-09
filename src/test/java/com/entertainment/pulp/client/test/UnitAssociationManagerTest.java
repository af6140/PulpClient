package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.UnitAssociationManager;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

/**
 * Created by dwang on 9/8/2016.
 */
public class UnitAssociationManagerTest extends BaseManagerTest {
    private UnitAssociationManager unitAssociationManager;
    public UnitAssociationManager getUnitAssociationManager() {
        return unitAssociationManager;
    }

    @Before

    public void setUp() {
        super.setUp();
        this.unitAssociationManager = new UnitAssociationManager();
        this.unitAssociationManager.setPulpClientConfiguration(this.getClientConfiguration());
        this.unitAssociationManager.setRestTemplate(this.getRestTemplate());
    }
    @Test
    public void testCopyPuppetModule(){
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.getUnitAssociationManager().getServicePath().replace("{repo_id}", "dest"));
        Matcher<String> sourceRepoMatcher  = StringContains.containsString("\"source_repo_id\":\"src\"");
        RequestMatcher contentMatcher = MockRestRequestMatchers.content().string(sourceRepoMatcher);
        this.getMockRestServiceServer().expect(requestMatcher).andExpect(contentMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.getUnitAssociationManager().copyPuppetModule("src", "dest", "af6140", "testdummy", "0.1.0");
        this.getMockRestServiceServer().verify();
    }

    @Test
    public void testDeleteNewerPuppetModule() {
        RequestMatcher requestMatcher = MockRestRequestMatchers.requestTo(this.getUnitAssociationManager().getDeleteServicePath().replace("{repo_id}", "test_repo"));
        Matcher<String> sourceRepoMatcher  = StringContains.containsString("\"$gt\":\"0.1.0\"");
        RequestMatcher contentMatcher = MockRestRequestMatchers.content().string(sourceRepoMatcher);
        this.getMockRestServiceServer().expect(requestMatcher).andExpect(contentMatcher).andRespond(MockRestResponseCreators.withSuccess());
        this.getUnitAssociationManager().deletNewerPuppetModule("test_repo", "af6140", "testdummy", "0.1.0");
        this.getMockRestServiceServer().verify();
    }
}
