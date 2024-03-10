package com.qingyou.auth.abac.util;

import com.qingyou.auth.abac.exception.AbacAuthException;

import java.util.Comparator;

public class CompareUtil {
    public static int compare(Object o1, Object o2) {
        if (o1 instanceof Comparable<?> && o2 instanceof Comparable<?>) {
            return o1.toString().compareTo(o2.toString());
        }
        throw new AbacAuthException("error comparing " + o1 + " and " + o2);
    }

    public static <T> int compare(T o1, T o2, Comparator<T> comparator) {
        return comparator.compare(o1, o2);
    }

}
