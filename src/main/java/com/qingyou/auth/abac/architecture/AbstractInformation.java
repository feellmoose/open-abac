package com.qingyou.auth.abac.architecture;

import com.qingyou.auth.abac.policy.Policy;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractInformation<V, O, T> implements Information<V, O, T> {
    protected final Supplier<List<Policy<V, O, T>>> policiesSource;
    protected List<Policy<V, O, T>> policies = Collections.emptyList();

    public AbstractInformation(Supplier<List<Policy<V, O, T>>> policiesSource) {
        this.policiesSource = policiesSource;
    }

    @Override
    public List<Policy<V, O, T>> getPolicies() {
        return policies;
    }

    @Override
    public List<Policy<V, O, T>> refreshPolicies() {
        this.policies = policiesSource.get();
        return policies;
    }
}
