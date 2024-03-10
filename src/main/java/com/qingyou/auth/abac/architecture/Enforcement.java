package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.exception.AbacAuthException;

public interface Enforcement {
    <V,O,T> void enforce(Attribute<V,O,T> attribute) throws AbacAuthException;
}