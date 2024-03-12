package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;

import java.util.List;

public final class Information implements com.qingyou.auth.api.Information<Visitor, Option, Target, Policy> {

    private List<Policy> cachedPolicies;

    private final PolicySource policySource;

    public Information(PolicySource policySource) {
        this.policySource = policySource;
        this.refresh();
    }

    @Override
    public List<Policy> policies() {
        return this.cachedPolicies;
    }

    @Override
    public Information refresh() {
        this.cachedPolicies = policySource.load();
        return this;
    }
}