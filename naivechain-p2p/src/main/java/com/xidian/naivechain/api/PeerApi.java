package com.xidian.naivechain.api;

import com.xidian.naivechain.model.Peer;

import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/9
 * each node connect with others
 * include check info of that node,list linked node,
 *          send message to specified node or all the nodes.
 */
public class PeerApi {

    public List<Peer> listPeers() {

        return null;
    }

    public Peer getPeerByHost(String host, Integer port) {
        return null;
    }

    public String sendMsgToPeer(String message, String host, Integer port) {

        return null;
    }

    public String broadMsg(String message) {

        return null;
    }

    public void initPeer(String host, String port) {

    }
}
