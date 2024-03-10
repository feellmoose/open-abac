package com.qingyou.auth.abac.attribute;


import java.util.List;

public record Visitor(Long id,String code, String name, Role role) {
    public enum Role {
        root,
        admin,
        user,
        visitor
    }
    public record Allow(Long id, String code, List<Role> roles) {
    }
}
