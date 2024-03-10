package com.qingyou.auth.abac.exception;

public class AbacBuildException extends RuntimeException {

    public AbacBuildException(String message) {
        super("Error build rules for matching contexts, " + message);
    }

    public AbacBuildException(String message, Exception e) {
        super("Error build rules for matching contexts, " + message, e);
    }
}
