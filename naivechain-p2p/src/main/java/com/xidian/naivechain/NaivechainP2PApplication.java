package com.xidian.naivechain;

import com.xidian.naivechain.config.PeerConfig;
import com.xidian.naivechain.model.Peer;
import com.xidian.naivechain.service.PeerService;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
public class NaivechainP2PApplication {

    public static void main(String[] args) {
        PeerService service = new PeerService();
        PeerConfig config = new PeerConfig();
        config.setHost("127.0.0.1");
        config.setPort(9000);
        service.init(config);
    }
}
