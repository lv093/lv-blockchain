package com.xidian.naivechain.service;

import com.xidian.naivechain.config.PeerConfig;
import com.xidian.naivechain.model.Peer;
import com.xidian.naivechain.task.PeerTask;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Slf4j
public class PeerService {

    private Peer peer;

    public void init(PeerConfig config) {
        this.peer = new Peer(config.getHost(), config.getPort(), config.getInterPeers());

        new Thread(new PeerTask(peer)).start();
    }


    public void sendMsg(String message) {
        List<Peer> interPeers = peer.getInterPeers();

        if (interPeers == null || interPeers.isEmpty()) {
            return;
        }

        for (Peer interPeer : interPeers) {
            try {
                Socket socket = new Socket(interPeer.getHost(), interPeer.getPort());
                Writer writer = new OutputStreamWriter(socket.getOutputStream());

                writer.write(message);
                writer.close();
                writer.flush();
            } catch (IOException e) {
                log.debug("send message: [{}] to {} , happens error-> {} .", message, interPeer, e.getMessage());
            }

        }
    }

    public String recvMsg() {
        return null;
    }


}
