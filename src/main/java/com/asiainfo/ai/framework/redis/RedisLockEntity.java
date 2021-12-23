package com.asiainfo.ai.framework.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RedisLockEntity {
    private String lockKey;
    private String requestId;
}
