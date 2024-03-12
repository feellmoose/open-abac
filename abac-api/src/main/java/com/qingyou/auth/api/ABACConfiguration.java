package com.qingyou.auth.api;


import com.qingyou.auth.api.architechure.Decision;
import com.qingyou.auth.api.attribute.Attribute;
import com.qingyou.auth.api.attribute.Option;
import com.qingyou.auth.api.attribute.Target;
import com.qingyou.auth.api.attribute.Visitor;
import com.qingyou.auth.api.policy.Policy;

public interface ABACConfiguration<V extends Visitor, O extends Option, T extends Target, P extends Policy, A extends Attribute, t> {
    ABACConfiguration<V, O, T, P, A, t> registerInformation(Information<V, O, T, P> information);

    ABACConfiguration<V, O, T, P, A, t> registerDecision(Decision<P, A> decision);

    ABACConfiguration<V, O, T, P, A, t> forClass(Class<? extends ABAC> clazz);

    Information<V, O, T, P> getInformation();

    Decision<P, A> getDecision();

    Class<? extends ABAC> getABACClass();

}
