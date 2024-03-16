package com.qingyou.auth.api;

import com.qingyou.auth.api.policy.Policy;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.List;
import java.util.function.Supplier;

public interface PolicySource<T extends Policy, P extends PolicySource<T, P>> {
    P registerRuleCreators(List<RuleCreator<Object, Object>> ruleCreators);

    P registerRuleCreator(RuleCreator<Object, Object> ruleCreator);

    P setStrSource(Supplier<String> strSource);

    P setPolicySerialize(PolicySerialize<T> policySerialize);

    List<T> load();
}
