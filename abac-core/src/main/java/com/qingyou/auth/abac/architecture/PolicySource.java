package com.qingyou.auth.abac.architecture;

import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public class PolicySource implements com.qingyou.auth.api.PolicySource<Policy, PolicySource> {

    private final List<RuleCreator<Object, Object>> ruleCreators = new CopyOnWriteArrayList<>();

    private Supplier<String> strSource;

    private PolicySerialize<Policy> policySerialize;

    @Override
    public PolicySource registerRuleCreators(List<RuleCreator<Object, Object>> ruleCreators) {
        this.ruleCreators.addAll(ruleCreators);
        return this;
    }

    @Override
    public PolicySource registerRuleCreator(RuleCreator<Object, Object> ruleCreator) {
        this.ruleCreators.add(ruleCreator);
        return this;
    }

    @Override
    public PolicySource setStrSource(Supplier<String> strSource) {
        this.strSource = strSource;
        return this;
    }

    @Override
    public PolicySource setPolicySerialize(PolicySerialize<Policy> policySerialize) {
        this.policySerialize = policySerialize;
        return this;
    }

    @Override
    public List<Policy> load() {
        return policySerialize.deserialize(strSource.get(), ruleCreators);
    }
}
