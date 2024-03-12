package com.qingyou.auth.api.policy;

import java.util.Map;

public record AttributeContext(Map<String, Object> values) implements Context {
}
