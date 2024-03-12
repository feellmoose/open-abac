package com.qingyou.auth.abac.serialize;

import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.List;

public class JsonPolicySerialize implements PolicySerialize<Policy> {
    private final JsonSerializer serializer;

    public JsonPolicySerialize(JsonSerializer serializer) {
        this.serializer = serializer;
    }

    //TODO

    @Override
    public String serialize(List<Policy> policies, List<RuleCreator<Object, Object>> creators) {
        return "";
    }

    @Override
    public List<Policy> deserialize(String serialized, List<RuleCreator<Object, Object>> creators) {
        return List.of();
    }
}
