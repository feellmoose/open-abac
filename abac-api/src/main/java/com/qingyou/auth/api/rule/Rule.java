package com.qingyou.auth.api.rule;

import java.util.function.BiPredicate;

public interface Rule<T> {
    boolean judge(T data);

    String name();

    Object param();

    class Builder<T> {
        private String name;
        private Object param;
        private BiPredicate<T, Object> rule;

        public Builder<T> withName(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> withParam(Object param) {
            this.param = param;
            return this;
        }

        public Builder<T> rule(BiPredicate<T, Object> rule) {
            this.rule = rule;
            return this;
        }


        public Rule<T> build() {
            return CustomRule.of(this.param, this.name, this.rule);
        }

    }


}