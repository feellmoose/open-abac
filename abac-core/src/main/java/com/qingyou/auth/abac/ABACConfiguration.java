package com.qingyou.auth.abac;

import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.ABAC;

public class ABACConfiguration implements com.qingyou.auth.api.ABACConfiguration<Visitor, Option, Target, Policy, Attribute, Object> {

    public ABACConfiguration() {
    }

    private Information defaultInformation;
    private Decision defaultDecision;
    private Class<? extends ABAC> aClass = com.qingyou.auth.abac.ABAC.class;

    @Override
    public ABACConfiguration registerInformation(com.qingyou.auth.api.Information<Visitor, Option, Target, Policy> information) {
        this.defaultInformation = (Information) information;
        return this;
    }

    @Override
    public ABACConfiguration registerDecision(com.qingyou.auth.api.architechure.Decision<Policy, Attribute> decision) {
        this.defaultDecision = (Decision) decision;
        return this;
    }

    @Override
    public ABACConfiguration forClass(Class<? extends ABAC> clazz) {
        this.aClass = clazz;
        return this;
    }

    @Override
    public Information getInformation() {
        return this.defaultInformation;
    }

    @Override
    public Decision getDecision() {
        return this.defaultDecision;
    }

    @Override
    public Class<? extends ABAC> getABACClass() {
        return this.aClass;
    }

}
