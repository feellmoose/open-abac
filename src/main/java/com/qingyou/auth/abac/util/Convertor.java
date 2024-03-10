package com.qingyou.auth.abac.util;


import com.qingyou.auth.abac.attribute.Attribute;

public interface Convertor<T, R, VA, OA, TA> {
    Attribute<VA, OA, TA> attribute(T t);
    R convertor(Attribute<VA, OA, TA> attribute);
}
