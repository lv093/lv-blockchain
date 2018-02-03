package com.xidian.naivechain;

import com.xidian.naivechain.config.PeerConfig;
import com.xidian.naivechain.model.Peer;
import com.xidian.naivechain.service.PeerService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */

public class PeerTest {

    @Before
    public void init() {

    }

    @Test
    public void test1() {
        PeerService sendClient = new PeerService();
        PeerConfig config = new PeerConfig();
        config.setPort(9001);
        config.setHost("127.0.0.1");
        List<Peer> peerList = new ArrayList<>(10);
        peerList.add(new Peer("127.0.0.1", 9000, null));
        config.setInterPeers(peerList);
        sendClient.init(config);

        String message = "hello block";
        sendClient.sendMsg(message);
    }
}
