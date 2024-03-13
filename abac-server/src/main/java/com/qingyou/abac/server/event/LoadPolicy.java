package com.qingyou.abac.server.event;

import com.qingyou.auth.abac.policy.Policy;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record LoadPolicy(UUID uuid, List<Policy> policies, Refresh refresh) implements Serializable {
}
