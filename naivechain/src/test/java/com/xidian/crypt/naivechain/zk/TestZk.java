package com.xidian.crypt.naivechain.zk;

import com.xidian.crypt.naivechain.redis.TestRedission;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/1.
 */
@Slf4j
public class TestZk {

    public static void main(String[] args) {
        new TestZk().initZk();
    }

    public void initZk() {
        ZooKeeper zooKeeper = null;
        try {
//            zooKeeper = new ZooKeeper("192.168.61.34:4181",
            zooKeeper = new ZooKeeper("192.168.60.126:2181",
                    1000,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent watchedEvent) {
                            log.info("zk processed->{}.", watchedEvent.toString());
                        }
                    });
        } catch (IOException e) {
            log.error("初始化zookeeper error->{}.", e);
        }

        try {
            List<String> children = zooKeeper.getChildren("/", false);
            if (children.isEmpty()) {
                log.info("no members");
            }
            log.info("zk children->{}.", children);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {

            zooKeeper.create("/zk", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.create("/testRootPath/testChildPath1", "testChildData1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            log.info(new String(zooKeeper.getData("/testRootPath", false, null)));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
