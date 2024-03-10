package com.qingyou.auth.abac.annotation;

public enum Target {
    None(0),
    Resources(1),
    Others(2),
    ;

    private final int code;

    int code() {
        return this.code;
    }

    Target(int code) {
        this.code = code;
    }


}
