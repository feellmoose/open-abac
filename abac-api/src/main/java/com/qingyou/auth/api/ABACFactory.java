package com.qingyou.auth.api;

public interface ABACFactory<C extends ABACConfiguration> {
    C getConfiguration();
    ABAC create();
}
