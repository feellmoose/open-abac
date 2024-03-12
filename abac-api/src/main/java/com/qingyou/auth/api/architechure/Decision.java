package com.qingyou.auth.api.architechure;


import com.qingyou.auth.api.attribute.Attribute;
import com.qingyou.auth.api.policy.Policy;
import com.qingyou.auth.api.policy.Strategy;

import java.util.List;

public interface Decision<P extends Policy, A extends Attribute> {

    default boolean decide(List<P> policies, A attribute) {
        var approvals = policies.stream().filter(defaultPolicy -> defaultPolicy.strategy().equals(Strategy.approval));
        var rejects = policies.stream().filter(defaultPolicy -> defaultPolicy.strategy().equals(Strategy.reject));
        return approvals.anyMatch(policy -> approval(policy, attribute))
                && rejects.noneMatch(policy -> reject(policy, attribute));
    }

    boolean approval(P policies, A attribute);

    boolean reject(P policies, A attribute);
}