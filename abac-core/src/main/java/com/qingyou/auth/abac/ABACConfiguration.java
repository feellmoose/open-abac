package com.qingyou.auth.abac;

import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

public class ABACConfiguration implements com.qingyou.auth.api.ABACConfiguration<Visitor, Option, Target, Policy, Attribute, Object> {

    public ABACConfiguration() {
    }

    private com.qingyou.auth.api.Information<Visitor, Option, Target, Policy> defaultInformation;
    private com.qingyou.auth.api.architechure.Decision<Policy, Attribute>  defaultDecision;
    private Class<? extends ABAC> aClass = com.qingyou.auth.abac.ABAC.class;
    private final List<RuleCreator<Object, Object>> ruleCreators = new CopyOnWriteArrayList<>();
    private Supplier<String> strSource;
    private PolicySerialize<Policy> policySerialize;

    @Override
    public ABACConfiguration registerInformation(com.qingyou.auth.api.Information<Visitor, Option, Target, Policy> information) {
        this.defaultInformation = information;
        return this;
    }

    @Override
    public ABACConfiguration registerDecision(com.qingyou.auth.api.architechure.Decision<Policy, Attribute> decision) {
        this.defaultDecision = decision;
        return this;
    }

    @Override
    public ABACConfiguration registerRuleCreators(List<RuleCreator<Object, Object>> ruleCreators) {
        this.ruleCreators.addAll(ruleCreators);
        return this;
    }

    @Override
    public ABACConfiguration registerRuleCreator(RuleCreator<Object, Object> ruleCreator) {
        this.ruleCreators.add(ruleCreator);
        return this;
    }

    @Override
    public ABACConfiguration setStrSource(Supplier<String> strSource) {
        this.strSource = strSource;
        return this;
    }

    @Override
    public ABACConfiguration setPolicySerialize(PolicySerialize<Policy> policySerialize) {
        this.policySerialize = policySerialize;
        return this;
    }

    @Override
    public ABACConfiguration forClass(Class<? extends ABAC> clazz) {
        this.aClass = clazz;
        return this;
    }

    @Override
    public com.qingyou.auth.api.Information<Visitor, Option, Target, Policy> getInformation() {
        return this.defaultInformation;
    }

    @Override
    public com.qingyou.auth.api.architechure.Decision<Policy, Attribute> getDecision() {
        return this.defaultDecision;
    }

    @Override
    public Class<? extends ABAC> getABACClass() {
        return this.aClass;
    }

    public List<RuleCreator<Object, Object>> getRuleCreators() {
        return ruleCreators;
    }

    public Supplier<String> getStrSource() {
        return strSource;
    }

    public PolicySerialize<Policy> getPolicySerialize() {
        return policySerialize;
    }

}
