package com.xidian.naivechain.config;

import com.xidian.naivechain.model.Peer;
import lombok.Data;

import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Data
public class PeerConfig {
    private String host;

    private Integer port;

    private List<Peer> interPeers;
}
