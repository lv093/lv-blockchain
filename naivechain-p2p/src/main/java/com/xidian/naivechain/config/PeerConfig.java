package com.xidian.naivechain.config;

import com.xidian.naivechain.model.Peer;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Data
@Configuration
public class PeerConfig {



    private String host;

    private Integer port;

    private List<Peer> interPeers;
}
