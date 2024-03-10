package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.annotation.Option;
import com.qingyou.auth.abac.annotation.Target;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;

public final class DefaultDecision implements Decision<Visitor.Allow, Option, Target, Visitor, Option, Target> {

    @Override
    public boolean approval(Policy<Visitor.Allow, Option, Target> policy, Attribute<Visitor, Option, Target> attribute) {
        return match(policy, attribute);
    }

    @Override
    public boolean reject(Policy<Visitor.Allow, Option, Target> policy, Attribute<Visitor, Option, Target> attribute) {
        return match(policy, attribute);
    }

    private boolean match(Policy<Visitor.Allow, Option, Target> policy, Attribute<Visitor, Option, Target> attribute) {
        var visitor = attribute.visitor();
        var allow = policy.allow();
        if (allow.id() != null && !allow.id().equals(visitor.id())) return false;
        if (allow.code() != null && !allow.code().equals(visitor.code())) return false;
        if (!allow.roles().isEmpty() && !allow.roles().contains(visitor.role())) return false;
        if (!policy.targets().isEmpty() && !policy.targets().contains(attribute.target())) return false;
        if (!policy.options().isEmpty() && !policy.options().contains(attribute.option())) return false;
        return attribute.contexts().entrySet().stream().allMatch(
                contexts -> {
                    var key = contexts.getKey();
                    var value = contexts.getValue();
                    var rule = policy.contexts().get(key);
                    return rule == null || rule.stream().allMatch(
                            r -> r.judge(value)
                    );
                }
        );

    }


}