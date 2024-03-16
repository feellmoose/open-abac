package com.qingyou.auth.api.attribute;

import com.qingyou.auth.api.policy.AttributeContext;
import com.qingyou.auth.api.policy.Context;

public interface Attribute {
    <T extends Option> T option();

    <T extends Target> T target();

    <T extends Visitor> T visitor();

    AttributeContext context();
}
