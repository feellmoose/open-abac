package com.qingyou.auth.api.rule;

import java.util.function.BiPredicate;

final class CustomRule<T> implements Rule<T> {
    private final Object param;
    private final String name;
    private final BiPredicate<T, Object> rule;

    private CustomRule(Object param, String name, BiPredicate<T, Object> rule) {
        this.param = param;
        this.name = name;
        this.rule = rule;
    }

    static <T> CustomRule<T> of(Object param, String name, BiPredicate<T, Object> rule) {
        return new CustomRule<>(param, name, rule);
    }

    public boolean judge(T data) {
        return this.rule.test(data, this.param);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Object param() {
        return this.param;
    }
}
