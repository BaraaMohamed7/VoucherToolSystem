package com.biro.vouchertoolsystem.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataScrubber {
    private final String MASK = "***";
    private final ObjectMapper objectMapper;
    private final List<String> sensitiveKeywords;

    public DataScrubber(ObjectMapper objectMapper,@Value("${logging.scrubber.sensitive-keys}") List<String> sensitiveKeywords) {
        this.objectMapper = objectMapper;
        this.sensitiveKeywords = sensitiveKeywords.stream().map(String::toLowerCase).toList();
    }

    public String scrub(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return jsonString;
        }
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            scrubNode(rootNode);
            return  objectMapper.writeValueAsString(rootNode);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return jsonString;
        }
    }

    public void scrubNode(JsonNode node) {
        if(node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            for (Map.Entry<String, JsonNode> property : objectNode.properties()) {
                String key = property.getKey();
                JsonNode value = property.getValue();
                if (sensitiveKeywords.contains(key)) {
                    objectNode.put(key, MASK);
                } else {
                    scrubNode(value);
                }
            }
        } else if (node.isArray()) {
            for (JsonNode value : node) {
                scrubNode(value);
            }
        }
    }
}
