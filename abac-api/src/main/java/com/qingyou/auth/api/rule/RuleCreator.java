package com.qingyou.auth.api.rule;


public interface RuleCreator<P, T> {
    Rule<T> create(String name, P param);
    String ruleType();
}
