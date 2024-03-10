package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.abac.policy.Strategy;

import java.util.List;

public interface Decision<V, O, T, VA, OA, TA> {
    default boolean decide(List<Policy<V, O, T>> policies, Attribute<VA, OA, TA> attribute) {
        var approvals = policies.stream().filter(policy -> policy.strategy().equals(Strategy.approval));
        var rejects = policies.stream().filter(policy -> policy.strategy().equals(Strategy.reject));
        return approvals.anyMatch(policy -> approval(policy, attribute))
                && rejects.noneMatch(policy -> reject(policy, attribute));
    }

    boolean approval(Policy<V, O, T> policies, Attribute<VA, OA, TA> attribute);

    boolean reject(Policy<V, O, T> policies, Attribute<VA, OA, TA> attribute);
}