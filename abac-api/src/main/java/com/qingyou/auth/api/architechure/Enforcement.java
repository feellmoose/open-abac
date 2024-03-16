package com.qingyou.auth.api.architechure;


import com.qingyou.auth.api.Result;
import com.qingyou.auth.api.attribute.Attribute;

public interface Enforcement {
    Result<Object> enforce(Attribute attribute);
}