package com.entertainment.pulp.client.test;

import com.entertainment.pulp.client.model.RPMUnitKey;
import com.fasterxml.jackson.databind.JsonNode;
import junit.framework.TestCase;

import java.util.Iterator;

/**
 * Created by dwang on 9/8/2016.
 */
public class RpmUnitKeyTest extends TestCase{
    private RPMUnitKey unitKey ;
    protected  void setUp() {
        this.unitKey=new RPMUnitKey();
        this.unitKey.name = "testrpm";
        this.unitKey.version = "0.1.0";
        this.unitKey.release = "1a";
        this.unitKey.epoch = 0;
        this.unitKey.architecture= "noarch";
    }

    public void testGetAssociateFilterJsonNode(){
        JsonNode node =unitKey.getAssociationFilterJsonNode();
        //System.out.println(node.toString());
        JsonNode unitName = node.path("filters").path("unit").path("$and").get(0).path("name");
        assert(unitName.textValue().equals(unitKey.name));
    }
    public void testGetUnAssociateNewerCriteriaJsonNode() {
        JsonNode node = unitKey.getUnAssociateNewerFilterJsonNode();
        //System.out.println(node.toString());
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
