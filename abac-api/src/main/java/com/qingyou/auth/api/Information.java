package com.qingyou.auth.api;

import com.qingyou.auth.api.attribute.Option;
import com.qingyou.auth.api.attribute.Target;
import com.qingyou.auth.api.attribute.Visitor;
import com.qingyou.auth.api.policy.Policy;

import java.util.List;

public interface Information<V extends Visitor, O extends Option, T extends Target, P extends Policy> {
    List<P> policies();
    Information<V, O, T, P> refresh();
}
