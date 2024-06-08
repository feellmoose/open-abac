package com.qingyou.auth.abac;


import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.rule.RuleCreators;
import com.qingyou.auth.abac.serialize.GsonPolicySerialize;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.policy.AttributeContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class DefaultStructureTests {

    static Attribute attribute = new Attribute(
            new Visitor(1L, "1212", "name", Visitor.Role.root),
            new Option("name"),
            new Target("name", 12),
            new AttributeContext(Map.of("time", "11:31"))
    );

    @Test
    void prepareAbac() {
        var abacFactory = new ABACFactory();
        var config = abacFactory.getConfiguration();
        config.registerInformation(new Information(new PolicySource()
                .setPolicySerialize(new GsonPolicySerialize())
                .registerRuleCreators(List.of(
                        RuleCreators.equal(),
                        RuleCreators.between()
                )).setStrSource(() ->
                        """
                                [{
                                   "visitors":[{
                                        "id":1
                                        },{
                                        "id":2
                                        }
                                        ],
                                   "context":{
                                       "rules":{
                                           "time":{"between":["11:30","15:20"]}
                                       }
                                   },
                                   "strategy":"approval"
                                }]"""
                )));
    }

    @Test
    void readConfig() {
        var abacFactory = new ABACFactory();
        var config = abacFactory.getConfiguration();
        config.registerInformation(new Information(new PolicySource()
                .setPolicySerialize(new GsonPolicySerialize())
                .registerRuleCreators(List.of(
                        RuleCreators.equal(),
                        RuleCreators.between()
                )).setStrSource(() ->
                        """
                                [{
                                   "visitors":[{
                                        "id":1
                                        },{
                                        "id":2
                                        }
                                        ],
                                   "context":{
                                       "rules":{
                                           "time":{"between":["11:30","15:20"]}
                                       }
                                   },
                                   "strategy":"approval"
                                }]"""
                )));
        ABAC abac = abacFactory.create();
        abac.enforce(attribute);
    }

}
