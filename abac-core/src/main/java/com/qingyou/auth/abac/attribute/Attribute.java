package com.qingyou.auth.abac.attribute;

import com.qingyou.auth.api.policy.AttributeContext;

public record Attribute(Visitor visitor,
                        Option option,
                        Target target,
                        AttributeContext context) implements com.qingyou.auth.api.attribute.Attribute {
}