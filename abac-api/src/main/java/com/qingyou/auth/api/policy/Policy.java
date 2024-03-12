package com.qingyou.auth.api.policy;

import com.qingyou.auth.api.attribute.Option;
import com.qingyou.auth.api.attribute.Target;
import com.qingyou.auth.api.attribute.Visitor;

import java.util.List;

public interface Policy {
    <T extends Visitor> List<T> visitors();

    <T extends Option> List<T> options();

    <T extends Target> List<T> targets();

    Context context();

    Strategy strategy();
}
