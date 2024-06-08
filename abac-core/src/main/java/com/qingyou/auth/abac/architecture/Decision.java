package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.policy.Policy;

public final class Decision implements com.qingyou.auth.api.architechure.Decision<Policy, Attribute> {

    private boolean match(Policy policy, Attribute attribute) {
        if (policy.visitors().stream().noneMatch(defaultVisitor -> {
            if (defaultVisitor.id() != null && !defaultVisitor.id().equals(attribute.visitor().id()))
                return false;
            if (defaultVisitor.name() != null && !defaultVisitor.name().equals(attribute.visitor().name()))
                return false;
            return defaultVisitor.code() == null || defaultVisitor.code().equals(attribute.visitor().code());
        })) return false;
        if (policy.targets() != null && !policy.targets().isEmpty() && !policy.targets().contains(attribute.target()))
            return false;
        if (policy.options() != null && !policy.options().isEmpty() && !policy.options().contains(attribute.option()))
            return false;
        var policies = policy.context().rules();
        return attribute.context().values().entrySet().stream()
                .allMatch(entry -> policies.get(entry.getKey()).entrySet().stream().allMatch(e -> e.getValue().judge(entry.getValue())));
    }

    @Override
    public boolean approval(Policy policies, Attribute attribute) {
        return this.match(policies, attribute);
    }

    @Override
    public boolean reject(Policy policies, Attribute attribute) {
        return this.match(policies, attribute);
    }

}