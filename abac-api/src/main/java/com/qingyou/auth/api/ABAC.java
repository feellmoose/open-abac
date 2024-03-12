package com.qingyou.auth.api;


import com.qingyou.auth.api.attribute.Attribute;

public interface ABAC {
    Result<Object> enforce(Attribute attribute);
}
