package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.model.PuppetUnitKey;
import com.fasterxml.jackson.databind.JsonNode;
import junit.framework.TestCase;

import java.util.Iterator;

/**
 * Created by dwang on 9/8/2016.
 */
public class PuppetUnitKeyTest extends TestCase {
    private PuppetUnitKey unitKey;

    protected void setUp() {
        this.unitKey = new PuppetUnitKey();
        this.unitKey.name = "test";
        this.unitKey.version = "0.1.0";
        this.unitKey.owner = "af6140";
    }

    public void testGetAssociationFilterJsonNode() {
        JsonNode node = unitKey.getAssociationCriteriaJsonNode();
        System.out.println(node.toString());
        JsonNode unitName = node.path("filters").path("unit").path("$and").get(0).path("name");
        assert (unitName.textValue().equals(unitKey.name));
    }
    public void testGetUnAssociateNewerCriteriaJsonNode() {
        JsonNode node = unitKey.getUnAssociateNewerCriteriaJsonNode();
        System.out.println(node.toString());
        //JsonNode unitName = node.path("filters").path("unit").path("$and").get(1).path("version").path("$gt");
        Iterator<JsonNode> it = node.path("filters").path("unit").path("$and").elements();
        boolean found =false;
        while(it.hasNext()){
            JsonNode currentNode = it.next();
            JsonNode versionNode = currentNode.findValue("version");
            if (versionNode !=null && versionNode.findValue("$gt")!=null ){
                found = true;
                assert(versionNode.path("$gt").textValue().equals(unitKey.version));
            }
        }
        assert(found);
    }
}
