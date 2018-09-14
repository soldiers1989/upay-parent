package com.upay.dao.cache;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.pactera.dipper.utils.codec.KryoUtil;


public class MybatisRedisCache implements Cache {
    private static Logger log = LoggerFactory.getLogger(MybatisRedisCache.class);

    private final String id;
    private final byte[] idBytesPattern;
    private final byte[] hKeyBytes;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static RedisTemplate<byte[], byte[]> redisTemplate;


    // mybatis cache key 规则
    // [hashcode:checksum:mappedStementId:offset:limit:executeSql:queryParams]

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        log.debug("MybatisRedisCache:id=" + id);
        this.id = id;
        this.idBytesPattern = ("*" + id + "*").getBytes();
        this.hKeyBytes = ("MYBATIS_SECOND_LEVEL_CACHE_" + id).getBytes();
    }


    @Override
    public String getId() {
        return id;
    }


    @Override
    public void putObject(final Object key, final Object value) {
        log.debug("putObject() id:[{}] , key:[{}]", id, key);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.hSet(hKeyBytes, KryoUtil.serialize(key), KryoUtil.serialize(value));
                } catch (IOException e) {
                    throw new RuntimeException("缓存序列化异常", e);
                }
                return null;
            }
        });
    }


    @Override
    public Object getObject(final Object key) {
        log.debug("getObject() id:[{}] , key:[{}]", id, key);
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return KryoUtil.deserialize(connection.hGet(hKeyBytes, KryoUtil.serialize(key)));
                } catch (IOException e) {
                    throw new RuntimeException("缓存序列化异常", e);
                }
            }
        });
    }


    @Override
    public Object removeObject(final Object key) {
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Long size = connection.hDel(hKeyBytes, KryoUtil.serialize(key));
                    return size;
                } catch (IOException e) {
                    throw new RuntimeException("缓存序列化异常", e);
                }
            }
        });
    }


    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> set = connection.keys(idBytesPattern);

                Iterator<byte[]> iter = set.iterator();
                while (iter.hasNext()) {
                    byte[] key = iter.next();
                    Long size = connection.del(key);
                    log.debug("clear() , key {} , size {}", key, size);
                }
                return null;
            }
        });
    }


    @Override
    public int getSize() {
        return redisTemplate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hKeys(hKeyBytes).size();
            }
        });
    }


    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }


    public void setRedisTemplate(RedisTemplate<byte[], byte[]> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }
}
