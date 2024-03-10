package com.qingyou.auth.abac.architecture;


import com.qingyou.auth.abac.policy.Policy;

import java.util.List;

public interface Information<V, O, T> {
    List<Policy<V, O, T>> getPolicies();
    List<Policy<V, O, T>> refreshPolicies();
}