package com.qingyou.auth.api.serialize;

import com.qingyou.auth.api.policy.Policy;
import com.qingyou.auth.api.rule.RuleCreator;

import java.util.List;


public interface PolicySerialize<T extends Policy>  {
    String serialize(List<T> policies, List<RuleCreator<Object, Object>> creators);
    List<T> deserialize(String serialized, List<RuleCreator<Object, Object>> creators);
}
