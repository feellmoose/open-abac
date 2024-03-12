package com.qingyou.auth.api.rule;

import java.util.function.Predicate;

final class CustomRule<T> implements Rule<T> {
    private final String name;
    private final Predicate<T> predicate;

    private CustomRule(String name, Predicate<T> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    static <T> CustomRule<T> of(String name, Predicate<T> rule) {
        return new CustomRule<>(name, rule);
    }

    public boolean judge(T data) {
        return this.predicate.test(data);
    }

    @Override
    public String name() {
        return this.name;
    }
}
