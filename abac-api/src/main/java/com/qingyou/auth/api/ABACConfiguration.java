package com.qingyou.auth.api;


import com.qingyou.auth.api.architechure.Decision;
import com.qingyou.auth.api.attribute.Attribute;
import com.qingyou.auth.api.attribute.Option;
import com.qingyou.auth.api.attribute.Target;
import com.qingyou.auth.api.attribute.Visitor;
import com.qingyou.auth.api.policy.Policy;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.List;
import java.util.function.Supplier;

public interface ABACConfiguration<V extends Visitor, O extends Option, T extends Target, P extends Policy, A extends Attribute, t> {
    ABACConfiguration<V, O, T, P, A, t> registerInformation(Information<V, O, T, P> information);

    ABACConfiguration<V, O, T, P, A, t> registerDecision(Decision<P, A> decision);

    ABACConfiguration<V, O, T, P, A, t> forClass(Class<? extends ABAC> clazz);

    ABACConfiguration<V, O, T, P, A, t> registerRuleCreators(List<RuleCreator<Object, Object>> ruleCreators);

    ABACConfiguration<V, O, T, P, A, t> registerRuleCreator(RuleCreator<Object, Object> ruleCreator);

    ABACConfiguration<V, O, T, P, A, t> setStrSource(Supplier<String> strSource);

    ABACConfiguration<V, O, T, P, A, t> setPolicySerialize(PolicySerialize<P> policySerialize);

    Information<V, O, T, P> getInformation();

    Decision<P, A> getDecision();

    Class<? extends ABAC> getABACClass();



}
