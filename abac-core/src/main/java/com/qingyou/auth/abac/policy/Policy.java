package com.qingyou.auth.abac.policy;


import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.api.policy.Strategy;
import com.qingyou.auth.api.rule.PolicyContext;

import java.util.List;

public record Policy
        (List<Visitor> visitors,
         List<Option> options,
         List<Target> targets,
         PolicyContext context,
         Strategy strategy) implements com.qingyou.auth.api.policy.Policy {
}