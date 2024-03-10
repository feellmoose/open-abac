package com.qingyou.auth.abac.policy;

public interface Rule<T> {
    boolean judge(T t);
}
