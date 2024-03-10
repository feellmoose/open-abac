package com.qingyou.auth.abac.policy;

import java.util.List;
import java.util.Map;

public record Policy<V, O, T>(V allow,
                              List<O> options,
                              List<T> targets,
                              Map<String, List<Rule<Object>>> contexts,
                              Strategy strategy) {
    public record DTO<V, O, T>(V allow,
                               List<O> options,
                               List<T> targets,
                               Map<String, Map<String, Object>> contexts,
                               Strategy strategy) {
    }
}