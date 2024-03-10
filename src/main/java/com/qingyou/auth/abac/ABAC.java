package com.qingyou.auth.abac;


import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Enforcement;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.exception.AbacAuthException;
import com.qingyou.auth.abac.policy.Policy;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public final class ABAC implements Enforcement {
    private final Decision decision;
    private final Information information;

    public <VA, OA, TA> void enforce(Supplier<Attribute<VA, OA, TA>> collector) throws AbacAuthException {
        this.enforce(collector.get());
    }

    public <VA, OA, TA, R> R enforce(Supplier<Attribute<VA, OA, TA>> collector, Function<Attribute<VA, OA, TA>, R> convertor) throws AbacAuthException {
        var attribute = collector.get();
        var res = convertor.apply(attribute);
        this.enforce(attribute);
        return res;
    }

    public <VA, OA, TA> void enforce(Attribute<VA, OA, TA> attribute) throws AbacAuthException {
        var policies = information.getPolicies();
        try {
            if (!this.decision.decide(policies, attribute))
                throw new AbacAuthException("ABAC: Error matching auth with current attribute", attribute);
        } catch (AbacAuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AbacAuthException("ABAC: Error matching auth with other exception!", attribute, e);
        }
    }

    public <V, O, T> List<Policy<V, O, T>> refresh() {
        return information.refreshPolicies();
    }

    private ABAC(Decision decision, Information information) {
        this.decision = decision;
        this.information = information;
    }

    public final static class Builder {
        private Decision decision;
        private Information information;


        public Builder information(Information information) {
            this.information = information;
            return this;
        }

        public Builder decision(Decision decision) {
            this.decision = decision;
            return this;
        }

        public ABAC build() {
            information.refreshPolicies();
            return new ABAC(decision, information);
        }

    }


}
