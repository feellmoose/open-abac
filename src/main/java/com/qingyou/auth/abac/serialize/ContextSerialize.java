package com.qingyou.auth.abac.serialize;

import com.qingyou.auth.abac.exception.AbacBuildException;
import com.qingyou.auth.abac.policy.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qingyou.auth.abac.policy.RuleCreators.rules;

public class ContextSerialize {

    public static Map<String, List<Rule<Object>>> fromPolicy(Map<String, Map<String, Object>> contexts) {
        var res = new HashMap<String, List<Rule<Object>>>();
        contexts.forEach((k, v) -> {
            var allRules = new ArrayList<Rule<Object>>();
            v.forEach((ik, iv) -> {
                var creator = rules.get(ik);
                if (creator == null)
                    throw new AbacBuildException("the rule [" + ik + "] for context [" + k + "] not exists");
                allRules.add(creator.apply(iv));
            });
            res.put(k, allRules);
        });
        return res;
    }


}
