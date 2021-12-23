package com.asiainfo.ai.framework.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
//@Service
public class RedisService {

//    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加锁，自旋重试三次
     *
     * @param entity 锁实体
     * @return
     */
    public boolean lock(RedisLockEntity entity) {
        boolean locked = false;
        int tryCount = 3;
        while (!locked && tryCount > 0) {
            locked = redisTemplate.opsForValue().setIfAbsent(entity.getLockKey(), entity.getRequestId(), 2, TimeUnit.MINUTES);
            tryCount--;
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                log.error("线程被中断" + Thread.currentThread().getId(), e);
            }
        }
        return locked;
    }

    /**
     * 非原子解锁，可能解别人锁，不安全
     *
     * @param redisLockEntity
     * @return
     */
    public boolean unlock(RedisLockEntity redisLockEntity) {
        if (redisLockEntity == null || redisLockEntity.getLockKey() == null || redisLockEntity.getRequestId() == null)
            return false;
        boolean releaseLock = false;
        String requestId = (String) redisTemplate.opsForValue().get(redisLockEntity.getLockKey());
        if (redisLockEntity.getRequestId().equals(requestId)) {
            releaseLock = redisTemplate.delete(redisLockEntity.getLockKey());
        }
        return releaseLock;
    }

    /**
     * 使用lua脚本解锁，不会解除别人锁
     *
     * @param redisLockEntity
     * @return
     */
    public boolean unlockLua(RedisLockEntity redisLockEntity) {
        if (redisLockEntity == null || redisLockEntity.getLockKey() == null || redisLockEntity.getRequestId() == null)
            return false;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        //用于解锁的lua脚本位置
        redisScript.setLocation(new ClassPathResource("unlock.lua"));
        redisScript.setResultType(Long.class);
        //没有指定序列化方式，默认使用上面配置的
        Object result = redisTemplate.execute(redisScript, Arrays.asList(redisLockEntity.getLockKey()), redisLockEntity.getRequestId());
        return result.equals(Long.valueOf(1));
    }

}