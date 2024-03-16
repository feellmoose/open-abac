package com.qingyou.auth.abac;


import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.api.Information;
import com.qingyou.auth.api.Result;
import com.qingyou.auth.api.architechure.Decision;
import com.qingyou.auth.api.architechure.Enforcement;

import java.time.LocalDateTime;


public final class ABAC implements Enforcement, com.qingyou.auth.api.ABAC {
    private Information information;
    private Decision decision;

    ABAC(Information information, Decision decision) {
        this.information = information;
        this.decision = decision;
    }

    @Override
    public Result<Object> enforce(com.qingyou.auth.api.attribute.Attribute attribute) {
        if (attribute == null) return Result.error("error");
        var policies = information.policies();
        try {
            if (attribute instanceof Attribute defaultAttribute && this.decision.decide(policies, defaultAttribute)) {
                return Result.success(LocalDateTime.now());
            }
            return Result.error("error");
        } catch (Exception e) {
            return Result.error("error");
        }
    }

}
