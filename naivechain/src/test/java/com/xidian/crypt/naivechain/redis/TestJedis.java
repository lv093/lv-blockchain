package com.xidian.crypt.naivechain.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author LvLiuWei
 * @date 2018/1/17.
 */
@Slf4j
public class TestJedis {
    public static volatile Integer result = 0;


    public JedisPool pool = new JedisPool("127.0.0.1");

    public static void main(String[] args) {
        new TestJedis().test();
    }

    public void test() {
        RedisHelper helper = new RedisHelper(pool.getResource());
        helper.setData("name", 0);
        Thread t1 = new Thread(new AddVal());
        Thread t2 = new Thread(new AddVal());

        Long time1 = System.currentTimeMillis();
        t1.start();
        t2.start();

        log.info("thread end!");
        Long time2 = System.currentTimeMillis();
        log.info("cost time->{}.", time2-time1);
        System.out.println(Thread.currentThread().getName() + "->:" + result);
    }

    class AddVal implements Runnable {
        private RedisHelper helper;
//        public AddVal(RedisHelper helper) {
//            this.helper = helper;
//        }

        @Override
        public void run() {
            RedisHelper helper = new RedisHelper(pool.getResource());

            for (int i=0; i<10000;i++) {
//                result = helper.getData("name");
                result = result+1;
                helper.setData("name", result);
            }
//            System.out.println(Thread.currentThread().getName() + " ->" + helper.getData("name"));
            List<String> resultList = helper.lgetData("name");
            for (String str:resultList) {
                log.info("{} ->redis list:{}.", Thread.currentThread().getName(), str);
            }
            System.out.println(Thread.currentThread().getName() + " redis 0->: " + resultList.get(0) + "redis last->: " + resultList.get(resultList.size()-1) + "->:" + result);
        }
    }
}
@Slf4j
class RedisHelper {
    private Jedis jedis;
    public RedisHelper(Jedis jedis) {
        this.jedis = jedis;
    }
    public void setData(String key, Integer value) {
        String watch = jedis.watch(key);
        Transaction multi = jedis.multi();
//        String currentData = multi.get(key).get();
        multi.rpush(key, String.valueOf(value));
        List<Object> result = multi.exec();
        if (result.size()==0) {
            this.setData(key, value);
        }
        log.info("{}--{}.", Thread.currentThread().getName(), result);
        jedis.unwatch();
//        jedis.rpush(key, String.valueOf(value)+"-");
//        jedis.set(key, String.valueOf(value));
    }
    public Integer getData(String key) {
        return Integer.valueOf(jedis.get(key));
    }

    public List<String> lgetData(String key) {
        return jedis.lrange(key, 0, -1);
    }

    void addData() {
        String watch = jedis.watch("name");
    }

}