package com.qingyou.auth.abac.rule;

import com.qingyou.auth.api.rule.Rule;

public abstract class AbstractRule<T> implements Rule<T> {
    private String name;
    private Object param;

    public AbstractRule(String name, Object param) {
        this.name = name;
        this.param = param;
    }

    abstract public boolean judge(T data);

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Object param() {
        return this.param;
    }

}
