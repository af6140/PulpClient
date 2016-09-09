package com.entertainment.pulp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by dwang on 9/7/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PuppetUnitKey implements UnitKey{
    @JsonProperty("name")
    public String name;
    @JsonProperty("vesion")
    public String version;
    @JsonProperty("author")
    public String owner;
    public PuppetUnitKey() {

    }
    public PuppetUnitKey(String name, String version, String owner) {
        this.name = name;
        this.version = version;
        this.owner = owner;
    }

    @JsonIgnore
    public JsonNode getAssociationCriteriaJsonNode() {
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
        ObjectNode ownerNode = factory.objectNode();
        ownerNode.put("author", owner);
        andNode.add( nameNode);
        andNode.add( versionNode);
        andNode.add( ownerNode);
        ObjectNode byUnitNode = factory.objectNode();
        byUnitNode.set("$and", andNode);
        ObjectNode filtersNode = factory.objectNode();
        filtersNode.set("unit", byUnitNode);
        rootNode.set("filters", filtersNode);
        return rootNode;
    }
    @JsonIgnore
    public JsonNode getUnAssociateNewerCriteriaJsonNode(){
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
        ObjectNode ownerNode = factory.objectNode();
        ownerNode.put("author", owner);
        andNode.add( nameNode);
        andNode.add( versionNode);
        andNode.add( ownerNode);
        ObjectNode byUnitNode = factory.objectNode();
        byUnitNode.set("$and", andNode);
        ObjectNode filtersNode = factory.objectNode();
        filtersNode.set("unit", byUnitNode);
        rootNode.set("filters", filtersNode);
        return rootNode;
    }
}
