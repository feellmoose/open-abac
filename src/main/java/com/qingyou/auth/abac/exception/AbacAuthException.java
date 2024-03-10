package com.qingyou.auth.abac.exception;


import com.qingyou.auth.abac.attribute.Attribute;

public class AbacAuthException extends RuntimeException {

    public AbacAuthException(String message) {
        super(message);
    }

    public <V, O, T> AbacAuthException(String message, Attribute<V, O, T> attribute) {
        super(message + ", with Attribute: " + attribute);
    }

    public <V, O, T> AbacAuthException(String message, Attribute<V, O, T> attribute, Exception e) {
        super(message + ", with Attribute: " + attribute, e);
    }
}