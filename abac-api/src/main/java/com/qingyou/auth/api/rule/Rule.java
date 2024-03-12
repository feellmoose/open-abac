package com.qingyou.auth.api.rule;

import java.util.function.Predicate;

public interface Rule<T> {
    boolean judge(T data);
    String name();

    class Builder<T> {
        private String name;
        private Predicate<T> predicate;

        public Builder<T> withName(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> rule(Predicate<T> predicate) {
            this.predicate = predicate;
            return this;
        }

        public Rule<T> build(){
            return CustomRule.of(this.name,this.predicate);
        }

    }


}