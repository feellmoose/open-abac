package com.qingyou.auth.abac.attribute;


public record Visitor(Long id, String code, String name, Role role) implements com.qingyou.auth.api.attribute.Visitor {
    public enum Role {
        root,
        admin,
        user,
        visitor
    }
}
