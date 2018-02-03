package com.xidian.crypt.naivechain.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.misc.RedissonThreadFactory;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/1/20.
 */
@Slf4j
public class TestRedission {

    public static Long result;
    public static final Jedis jedis = new Jedis();

    public static void main(String[] args) {
        result = 0L;
        RedissonClient client = Redisson.create();
        client.getAtomicLong("atomData").set(0);
        new TestRedission().test();
    }

    void test() {
        RedisThread thread1 = new RedisThread();
        RedisThread thread2 = new RedisThread();
        thread1.run();
        thread2.run();
    }

    class RedisThread implements Runnable {

        @Override
        public void run() {
            RedissonHelpler helpler = new RedissonHelpler(Redisson.create());
            for (int i=0;i<100000;i++) {
                helpler.setData("atomData", 1L);
                result++;
            }
            log.info("{}-->{}", Thread.currentThread().getName(), result);
        }
    }


    class RedissonHelpler  {
        private RedissonClient client;

        public RedissonHelpler(RedissonClient client) {
            this.client = client;
        }

        void setData(String key, Long value) {
            RAtomicLong atomicLong = client.getAtomicLong(key);
            atomicLong.addAndGet(value);
        }
    }
}
