package com.qingyou.auth.abac;

import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.exception.AbacBuildException;
import com.qingyou.auth.abac.serialize.GsonSerializer;
import com.qingyou.auth.abac.serialize.JsonPolicySerialize;

public class ABACFactory implements com.qingyou.auth.api.ABACFactory<ABACConfiguration> {

    private final ABACConfiguration abacConfiguration;

    public ABACFactory() {
        this.abacConfiguration = new ABACConfiguration()
                .registerDecision(new Decision())
                .registerInformation(new Information(new PolicySource()
                        .setPolicySerialize(new JsonPolicySerialize(new GsonSerializer()))));
    }


    @Override
    public ABACConfiguration getConfiguration() {
        return this.abacConfiguration;
    }

    @Override
    public com.qingyou.auth.api.ABAC create() {
        var des = abacConfiguration.getDecision();
        var inf = abacConfiguration.getInformation();
        if (des != null && inf != null) {
            return new ABAC(inf, des);
        }
        throw new AbacBuildException("error configuring ABACFactory, please check configuration, decision or information is null");
    }
}
