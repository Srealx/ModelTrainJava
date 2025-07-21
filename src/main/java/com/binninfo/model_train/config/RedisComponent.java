package com.binninfo.model_train.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisKeyCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RedisComponent
 *
 * @Author bn-gaopp
 * @Date 2023/5/24 09:32
 **/
@Component
public class RedisComponent {
    private static final Logger log = LoggerFactory.getLogger(RedisComponent.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name:default}")
    private String applicationName = "default";
    @Value("${project.site.name:default}")
    private String projectSiteName = "default";

    public static final long NOT_EXPIRE = -1L;

    public RedisComponent() {
    }

    public boolean existsKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public void setKey(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public void setKey(String key, String value, Long expireTime) {
        this.redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void setObjectKey(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public void setObjectKey(String key, Object value, Long expireTime) {
        this.redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public Object getObjectByKey(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public String getByKey(String key) {
        return (String) this.redisTemplate.opsForValue().get(key);
    }

    public void putAllHash(String key, Map hashMap) {
        this.redisTemplate.opsForHash().putAll(key, hashMap);
    }

    public Map getHashMap(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public void putHashByKey(String key, String hashKey, Object value) {
        this.redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashByKey(String key, String hashKey) {
        return this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public void incrementByKey(String key, String hashKey) {
        this.redisTemplate.opsForHash().increment(key, hashKey, 1L);
    }

    public void decrementByKey(String key, String hashKey) {
        this.redisTemplate.opsForHash().increment(key, hashKey, -1L);
    }

    public void incrementByKey(String key) {
        this.redisTemplate.opsForValue().increment(key);
    }

    public void incrementByKey(String key, long delta) {
        this.redisTemplate.opsForValue().increment(key, delta);
    }

    public void renameKey(String oldKey, String newKey) {
        this.redisTemplate.rename(oldKey, newKey);
    }

    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return this.redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    public void deleteKey(String key) {
        this.redisTemplate.delete(key);
    }

    public void deleteKey(String... keys) {
        Set<String> kSet = (Set) Stream.of(keys).map((k) -> {
            return k;
        }).collect(Collectors.toSet());
        this.redisTemplate.delete(kSet);
    }

    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = (Set) keys.stream().map((k) -> {
            return k;
        }).collect(Collectors.toSet());
        this.redisTemplate.delete(kSet);
    }

    public void expireKey(String key, long time, TimeUnit timeUnit) {
        this.redisTemplate.expire(key, time, timeUnit);
    }

    public void expireKeyAt(String key, Date date) {
        this.redisTemplate.expireAt(key, date);
    }

    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public void persistKey(String key) {
        this.redisTemplate.persist(key);
    }

    public void rPushListByKey(String key, Object value) {
        this.redisTemplate.opsForList().rightPush(key, value);
    }

    public Object lPopListByKey(String key) {
        return this.redisTemplate.opsForList().leftPop(key, 10L, TimeUnit.SECONDS);
    }

    public Object lPopListByKeyNotTime(String key) {
        return this.redisTemplate.opsForList().leftPop(key);
    }


    public void lPushListByKey(String key, Object value) {
        this.redisTemplate.opsForList().rightPush(key, value);
    }

    public void rPushAllListByKey(String key, List value) {
        this.redisTemplate.opsForList().rightPushAll(key, value);
    }

    public Object getListByKey(String key) {
        return this.redisTemplate.opsForList().range(key, 0L, -1L);
    }

    public Long getListSize(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    public Object putListKey(String key, String value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    public void setZsetKey(String key, Object value, double score) {
        this.redisTemplate.persist(key);
        this.redisTemplate.opsForZSet().add(key, value, score);
    }

    public Boolean putIfAbsent(String rkey, String key, String value) {
        return this.redisTemplate.opsForHash().putIfAbsent(rkey, key, value);
    }

    public void deleteHashkey(String key, Object... keys) {
        this.redisTemplate.opsForHash().delete(key, keys);
    }

    public Long getIncrementNum(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
        Long counter = entityIdCounter.incrementAndGet();
        if (null == counter || counter == 1L) {
            entityIdCounter.expire(1L, TimeUnit.DAYS);
        }

        return counter;
    }

    public Object ping() {
        return this.redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.ping();
            }
        });
    }

    public boolean lock(String lock, String value, long time, TimeUnit timeUnit) {
        return this.redisTemplate.opsForValue().setIfAbsent(lock, value, time, timeUnit);
    }

    public boolean releaseLock(String lock, String value) {
        Object key = this.redisTemplate.opsForValue().get(lock);
        if (value.equals(key)) {
            this.redisTemplate.delete(lock);
            return true;
        } else {
            return false;
        }
    }

    public Set<Object> getZsetByScore(String key, long startScore, long endScore, int num) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, (double) startScore, (double) endScore, 0L, (long) num);
    }

    public void removeZset(String key, Object... val) {
        Long count = this.redisTemplate.opsForZSet().remove(key, val);
        log.info("==== remove zset'nums: {}", count);
    }

    public boolean addZset(String key, String value, long score) {
        return this.redisTemplate.opsForZSet().add(key, value, (double) score);
    }

    public Long sAdd(String key, String... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Long sRemove(String key, Object... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    public Object sPop(String key) {
        return this.redisTemplate.opsForSet().pop(key);
    }

    public Boolean sMove(String key, String value, String destKey) {
        return this.redisTemplate.opsForSet().move(key, value, destKey);
    }

    public Long sSize(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    public Boolean sIsMember(String key, Object value) {
        return this.redisTemplate.opsForSet().isMember(key, value);
    }

    public Set<Object> sIntersect(String key, String otherKey) {
        return this.redisTemplate.opsForSet().intersect(key, otherKey);
    }

    public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    public Set<Object> sUnion(String key, String otherKeys) {
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Set<Object> sUnion(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    public Set<Object> sDifference(String key, String otherKey) {
        return this.redisTemplate.opsForSet().difference(key, otherKey);
    }

    public Set<Object> sDifference(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public Long sDifference(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    public Set<Object> getMemberSet(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public Object sRandomMember(String key) {
        return this.redisTemplate.opsForSet().randomMember(key);
    }

    public List<Object> sRandomMembers(String key, long count) {
        return this.redisTemplate.opsForSet().randomMembers(key, count);
    }

    public Set<Object> sDistinctRandomMembers(String key, long count) {
        return this.redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    public static String getLocalIP() throws Exception {
        InetAddress addr = InetAddress.getLocalHost();
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";

        for (int i = 0; i < ipAddr.length; ++i) {
            if (i > 0) {
                ipAddrStr = ipAddrStr + ".";
            }

            ipAddrStr = ipAddrStr + (ipAddr[i] & 255);
        }

        return ipAddrStr;
    }

    public List<String> scanKeysSimple(String pattern) {
        List<String> keys = (List) this.redisTemplate.execute((connection) -> {
            RedisKeyCommands keyCmds = connection.keyCommands();
            ScanOptions scanOpts = ScanOptions.scanOptions().match(pattern).count(1000L).build();
            Cursor<byte[]> cursor = keyCmds.scan(scanOpts);
            HashSet set = new HashSet();

            while (cursor.hasNext()) {
                byte[] bytes = (byte[]) cursor.next();
                set.add(new String(bytes, StandardCharsets.UTF_8));
            }

            return new ArrayList(set);
        }, true);
        log.info("scan keys return {} count", CollectionUtils.isEmpty(keys) ? 0 : keys.size());
        return keys;
    }

    public String buildRedisKey(String bizType, String... bizUids) {
        StringBuffer stringBuffer = this.getRedisKeyPrefix().append(bizType);
        String[] var4 = bizUids;
        int var5 = bizUids.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String str = var4[var6];
            stringBuffer.append(":").append(str);
        }

        return stringBuffer.toString();
    }

    public StringBuffer getRedisKeyPrefix() {
        return (new StringBuffer(this.applicationName)).append(":").append(this.projectSiteName).append(":");
    }
}
