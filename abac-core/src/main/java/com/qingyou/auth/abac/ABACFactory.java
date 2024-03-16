package com.qingyou.auth.abac;

import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.exception.AbacBuildException;

public class ABACFactory implements com.qingyou.auth.api.ABACFactory<ABACConfiguration> {

    private final ABACConfiguration abacConfiguration;

    public ABACFactory() {
        this.abacConfiguration = new ABACConfiguration()
                .registerDecision(new Decision())
                .registerInformation(new Information(null));
    }

    @Override
    public ABACConfiguration getConfiguration() {
        return this.abacConfiguration;
    }

    @Override
    public com.qingyou.auth.api.ABAC create() {
        var des = abacConfiguration.getDecision();
        var inf = abacConfiguration.getInformation();
        if (inf == null) inf = new Information(new PolicySource()
                .setStrSource(abacConfiguration.getStrSource())
                .registerRuleCreators(abacConfiguration.getRuleCreators())
                .setPolicySerialize(abacConfiguration.getPolicySerialize()));
        if (des != null && inf.getPolicySource() != null) {
            inf.refresh();
            return new ABAC(inf, des);
        }
        throw new AbacBuildException("error configuring ABACFactory, please check configuration, decision or information is null");
    }
}
