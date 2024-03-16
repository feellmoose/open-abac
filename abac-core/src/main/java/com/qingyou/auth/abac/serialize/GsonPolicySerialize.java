package com.qingyou.auth.abac.serialize;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.exception.AbacBuildException;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.policy.Strategy;
import com.qingyou.auth.api.rule.PolicyContext;
import com.qingyou.auth.api.rule.Rule;
import com.qingyou.auth.api.rule.RuleCreator;
import com.qingyou.auth.api.serialize.PolicySerialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonPolicySerialize implements PolicySerialize<Policy> {

    private final Gson gson = new Gson();

    record PolicyDTO(List<Visitor> visitors, List<Option> options, List<Target> targets, PolicyContext.DTO context,
                     Strategy strategy) implements com.qingyou.auth.api.policy.Policy {
        public static PolicyDTO fromPolicy(Policy policy, List<RuleCreator<Object, Object>> creators) {
            Map<String, Map<String, Object>> rules = new HashMap<>();
            var context = policy.context();
            if(context.rules() == null) throw new AbacBuildException("no policies context rules in read policies");
            context.rules().forEach((name, value) -> {
                var map = rules.getOrDefault(name, new HashMap<>());
                value.forEach((ruleType, rule) -> {
                    var param = rule.param();
                    map.put(ruleType, param);
                });
                rules.put(name, map);
            });
            return new PolicyDTO(policy.visitors(), policy.options(), policy.targets(), new PolicyContext.DTO(rules), policy.strategy());
        }

        public Policy toPolicy(List<RuleCreator<Object, Object>> creators) {
            Map<String, Map<String, Rule<Object>>> rules = new HashMap<>();
            var context = this.context;
            if(context.rules() == null) throw new AbacBuildException("no policies context rules in this file");
            context.rules().forEach((name, value) -> {
                var map = rules.getOrDefault(name, new HashMap<>());
                value.forEach((ruleType, param) -> {
                    var creator = creators.stream().filter(c -> c.ruleType().equals(ruleType)).findAny().orElseThrow(() -> new AbacBuildException("No rule found for " + ruleType));
                    map.put(ruleType, creator.create(name, param));
                });
                rules.put(name, map);
            });
            return new Policy(this.visitors(), this.options(), this.targets(), new PolicyContext(rules), this.strategy());
        }
    }

    @Override
    public String serialize(List<Policy> policies, List<RuleCreator<Object, Object>> creators) {
        var list = policies.stream().map(policy -> PolicyDTO.fromPolicy(policy, creators)).toList();
        return gson.toJson(list);
    }

    @Override
    public List<Policy> deserialize(String serialized, List<RuleCreator<Object, Object>> creators) {
        List<PolicyDTO> list = gson.fromJson(serialized, new TypeToken<>() {
        });
        return list.stream().map(policyDTO -> policyDTO.toPolicy(creators)).toList();

    }
}
