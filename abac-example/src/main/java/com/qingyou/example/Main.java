package com.qingyou.example;


import com.qingyou.auth.abac.ABACFactory;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.serialize.GsonPolicySerialize;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.policy.AttributeContext;
import com.qingyou.auth.api.rule.Rule;
import com.qingyou.auth.api.rule.RuleCreator;

import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        ABACFactory abacFactory = new ABACFactory();
        var list = List.of(new RuleCreator<>() {

            @Override
            public Rule<Object> create(String name, Object param) {
                return new Rule.Builder<>()
                        .withName(name)
                        .withParam(param)
                        .rule(Object::equals)
                        .build();
            }

            @Override
            public String ruleType() {
                return "equal";
            }
        });


        abacFactory.getConfiguration()
                .registerInformation(new Information(new PolicySource()
                        .setPolicySerialize(new GsonPolicySerialize())
                        .registerRuleCreators(list)
                        .setStrSource(() ->
                                """
                                        [{
                                           "visitors":[{
                                                "id":2
                                                }
                                           ],
                                           "context":{
                                               "rules":{
                                                   "time":{"equal":"12:30"}
                                               }
                                           },
                                           "strategy":"approval"
                                        }]"""
                        )));
        ABAC abac = abacFactory.create();
        Attribute attribute = new Attribute(
                new Visitor(1L, "1212", "name", Visitor.Role.root),
                new Option("name"),
                new Target("name", 12),
                new AttributeContext(Map.of("time", "12:30")));
        System.out.println(abac.enforce(attribute).success());



    }
}
