package com.qingyou.auth.abac.attribute;

import java.util.Map;

public record Attribute<V, O, T>(V visitor, O option, T target, Map<String, Object> contexts) {
}