package com.qingyou.abac.server.event;

import java.io.Serializable;
import java.util.UUID;

public record Refresh(UUID uuid) implements Serializable {
}
