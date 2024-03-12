package com.qingyou.auth.api.rule;

import com.qingyou.auth.api.policy.Context;

import java.util.Map;

public record PolicyContext(Map<String, Map<String, Rule<Object>>> rules) implements Context {
    public record DTO(Map<String, Map<String, Object>> rules) implements Context {
    }
}
