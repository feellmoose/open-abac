package com.qingyou.auth.abac.annotation;

import java.lang.annotation.ElementType;

@java.lang.annotation.Target(ElementType.METHOD)
public @interface ABAC {
    String target();
    String option();
}
