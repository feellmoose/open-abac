package com.qingyou.example;


import com.google.gson.Gson;
import com.qingyou.auth.abac.ABACFactory;
import com.qingyou.auth.abac.architecture.Information;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.serialize.GsonSerializer;
import com.qingyou.auth.abac.serialize.JsonPolicySerialize;
import com.qingyou.auth.abac.serialize.JsonSerializer;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.rule.Rule;
import com.qingyou.auth.api.rule.RuleCreator;

public class Main {


    public static void main(String[] args) {
        ABACFactory abacFactory = new ABACFactory();
        abacFactory.getConfiguration()
                .registerInformation(new Information(new PolicySource()
                        .setPolicySerialize(new JsonPolicySerialize(new JsonSerializer() {
                            final Gson gson = new Gson();
                            @Override
                            public <T> T fromJson(String json, Class<T> clazz) {
                                return gson.fromJson(json, clazz);
                            }
                            @Override
                            public <T> String toJson(T object) {
                                return gson.toJson(object);
                            }
                        }))
                        .registerRuleCreator(new RuleCreator<>() {
                            @Override
                            public Rule<Object> create(String name, Object param) {
                                return new Rule.Builder<>()
                                        .withName(name)
                                        .rule(param::equals)
                                        .build();
                            }
                            @Override
                            public String ruleType() {
                                return "equal";
                            }
                        })
                        .setStrSource(() ->
                                """
                                        """
                        )));
        ABAC abac = abacFactory.create();
        System.out.println(abac.enforce(new Attribute()).success());
    }
}
