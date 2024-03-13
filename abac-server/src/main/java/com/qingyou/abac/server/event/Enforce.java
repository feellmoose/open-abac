package com.qingyou.abac.server.event;

import com.qingyou.auth.abac.attribute.Attribute;

import java.io.Serializable;
import java.util.UUID;

public record Enforce(UUID uuid, Attribute attribute) implements Serializable {
}
