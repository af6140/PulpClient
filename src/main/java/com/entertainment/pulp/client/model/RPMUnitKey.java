package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;


/**
 * Created by dwang on 9/7/2016.
 */
public class RPMUnitKey implements  UnitKey {
    @JsonProperty("name")
    public String name;
    @JsonProperty("vesion")
    public String version;
    @JsonProperty("release")
    public String release;
    @JsonProperty("epoch")
    public int epoch =0;
    @JsonProperty("architecture")
    public String architecture;

//    @JsonIgnore
//    public String getAssociationFilter2() {
//        String filter=null;
//        JsonFactory jsonFactory =new JsonFactory();
//
//        StringBuffer buffFilter =new StringBuffer();
//        buffFilter.append("{ 'unit': { '$and': [ {'name': '").append(name).append("' , { 'version': ").append(version)
//                .append("'} , {'release': '").append(release).append("'}, ")
//                .append("{'epoch': ").append(epoch).append("}, ")
//                .append(" {'architecture': '").append(architecture).append("'} ] }");
//        filter = buffFilter.toString();
//        return filter;
//    }
    @JsonIgnore
    public JsonNode getAssociationFilterJsonNode() {
        final JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode rootNode = factory.objectNode();

        ArrayNode typeIdsNode = factory.arrayNode();
        typeIdsNode.add(factory.textNode("puppet_module"));
        rootNode.set("type_ids",typeIdsNode );

        ArrayNode andNode = factory.arrayNode();
        ObjectNode nameNode = factory.objectNode();
        nameNode.put("name", name);
        ObjectNode versionNode = factory.objectNode();
        versionNode.put("version", version);
        ObjectNode releaseNode = factory.objectNode();
        releaseNode.put("release", release);
        ObjectNode epochNode = factory.objectNode();
        epochNode.put("epoch",  epoch);
        ObjectNode archNode = factory.objectNode();
        archNode.put("architecture", architecture);
        andNode.add( nameNode);
        andNode.add( versionNode);
        andNode.add( releaseNode);
        andNode.add( epochNode);
        andNode.add( archNode);
        ObjectNode byUnitNode = factory.objectNode();
        byUnitNode.set("$and", andNode);
        ObjectNode filtersNode = factory.objectNode();
        filtersNode.set("unit", byUnitNode);
        rootNode.set("filters", filtersNode);

        return rootNode;

    }
    @JsonIgnore
    public JsonNode getUnAssociateNewerFilterJsonNode() {
        final JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode rootNode = factory.objectNode();

        ArrayNode typeIdsNode = factory.arrayNode();
        typeIdsNode.add(factory.textNode("puppet_module"));
        rootNode.set("type_ids",typeIdsNode );

        ArrayNode andNode = factory.arrayNode();
        ObjectNode nameNode = factory.objectNode();
        nameNode.put("name", name);
        ObjectNode verCmpNode = factory.objectNode();
        verCmpNode.put("$gt", version);
        ObjectNode versionNode = factory.objectNode();
        versionNode.set("version", verCmpNode);
        ObjectNode releaseNode = factory.objectNode();
        releaseNode.put("release", release);
        ObjectNode epochNode = factory.objectNode();
        epochNode.put("epoch", epoch);
        ObjectNode archNode = factory.objectNode();
        archNode.put("architecture", architecture);
        andNode.add( nameNode);
        andNode.add( versionNode);
        andNode.add( releaseNode);
        andNode.add( epochNode);
        andNode.add( archNode);
        ObjectNode byUnitNode = factory.objectNode();
        byUnitNode.set("$and", andNode);
        ObjectNode filtersNode = factory.objectNode();
        filtersNode.set("unit", byUnitNode);
        rootNode.set("filters", filtersNode);

        return rootNode;

    }

}
