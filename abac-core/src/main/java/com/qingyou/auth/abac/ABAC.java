package com.qingyou.auth.abac;


import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.api.Result;
import com.qingyou.auth.api.architechure.Enforcement;


public final class ABAC implements Enforcement, com.qingyou.auth.api.ABAC {
    private Information information;
    private Decision decision;

    ABAC(Information information, Decision decision) {
        this.information = information;
        this.decision = decision;
    }

    @Override
    public Result<Object> enforce(com.qingyou.auth.api.attribute.Attribute attribute) {
        var policies = information.policies();
        try {
            if (attribute instanceof Attribute defaultAttribute && this.decision.decide(policies, defaultAttribute)) {
                return Result.success(1);
            }
            return Result.error("error");
        } catch (Exception e) {
            return Result.error("error");
        }
    }

}
