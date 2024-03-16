package com.qingyou.auth.abac.rule;

import com.qingyou.auth.abac.util.CompareUtil;
import com.qingyou.auth.api.rule.Rule;
import com.qingyou.auth.api.rule.RuleCreator;

import java.util.List;
import java.util.Objects;

public final class RuleCreators {

    public static List<RuleCreator<Object, Object>> defaultCreators() {
        return List.of(
                RuleCreators.equal(),
                RuleCreators.between(),
                RuleCreators.greaterOrEqual(),
                RuleCreators.greaterThan(),
                RuleCreators.lessThan(),
                RuleCreators.lessOrEqual());
    }

    public static RuleCreator<Object, Object> equal() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return Objects.equals(data, param);
                    }
                };
            }

            @Override
            public String ruleType() {
                return "equal";
            }
        };
    }

    public static RuleCreator<Object, Object> between() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return param instanceof List<?> params
                                && params.size() == 2
                                && CompareUtil.compare(data, params.get(0)) * CompareUtil.compare(data, params.get(1)) <= 0;
                    }
                };
            }

            @Override
            public String ruleType() {
                return "between";
            }
        };
    }

    public static RuleCreator<Object, Object> greaterThan() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return CompareUtil.compare(data, param) > 0;
                    }
                };
            }

            @Override
            public String ruleType() {
                return "greaterThan";
            }
        };
    }

    public static RuleCreator<Object, Object> lessThan() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return CompareUtil.compare(data, param) < 0;
                    }
                };
            }

            @Override
            public String ruleType() {
                return "lessThan";
            }
        };
    }

    public static RuleCreator<Object, Object> greaterOrEqual() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return CompareUtil.compare(data, param) >= 0;
                    }
                };
            }

            @Override
            public String ruleType() {
                return "greaterThanOrEqual";
            }
        };
    }

    public static RuleCreator<Object, Object> lessOrEqual() {
        return new RuleCreator<>() {
            @Override
            public Rule<Object> create(String name, Object param) {
                return new AbstractRule<>(name, param) {
                    @Override
                    public boolean judge(Object data) {
                        return CompareUtil.compare(data, param) <= 0;
                    }
                };
            }

            @Override
            public String ruleType() {
                return "lessThanOrEqual";
            }
        };
    }


}
