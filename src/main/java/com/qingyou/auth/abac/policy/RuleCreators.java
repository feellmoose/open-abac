package com.qingyou.auth.abac.policy;

import com.qingyou.auth.abac.exception.AbacBuildException;
import com.qingyou.auth.abac.util.CompareUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface RuleCreators<T, U> extends Function<T, Rule<U>> {

    Map<String, RuleCreators<Object, Object>> rules = new HashMap<>(Map.of(
            "eq", param -> (t -> t.equals(param)),
            "ne", param -> (t -> !t.equals(param)),
            "contains", RuleCreators.match((o, params) -> params.contains(o)),
            "between", RuleCreators.between(),
            "gt", param -> o -> CompareUtil.compare(param, o) < 0,
            "lt", param -> o -> CompareUtil.compare(param, o) > 0,
            "ge", param -> o -> CompareUtil.compare(param, o) <= 0,
            "le", param -> o -> CompareUtil.compare(param, o) >= 0
    ));

    @SuppressWarnings("unchecked")
    static RuleCreators<Object, Object> match(BiPredicate<Object, List<Object>> predicate) {
        return param -> (Rule<Object>) t -> param instanceof List<?> params && predicate.test(t, (List<Object>) params);
    }

    static <T> RuleCreators<List<T>, T> between(Comparator<T> comparator) {
        return params -> t -> params.size() > 1 &&
                CompareUtil.compare(params.get(0), t, comparator) * CompareUtil.compare(params.get(1), t, comparator) < 1;
    }

    static RuleCreators<Object, Object> between() {
        return param -> {
            if (param instanceof List<?> params && params.size() > 1)
                return t -> CompareUtil.compare(params.get(0), t) * CompareUtil.compare(params.get(1), t) < 1;
            throw new AbacBuildException("between rule for [" + param + "], type [" + param.getClass() + "] is not allowed, param must be a list of size 1");
        };
    }


}
