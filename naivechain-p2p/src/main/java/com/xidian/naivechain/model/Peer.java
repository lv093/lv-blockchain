package com.xidian.naivechain.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 */
@Data
public class Peer {

    private Logger logger = LoggerFactory.getLogger(Peer.class);

    private String host;

    private Integer port;

    private List<Peer> interPeers;

    private ServerSocket serverSocket;

    public Peer(String host, Integer port) {
        this(host, port, null);
    }

    public Peer(String host, Integer port, List<Peer> interPeers) {
        this.host = host;
        this.port = port;
        this.interPeers = interPeers;
        this.createConn();
    }

    private void createConn() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.info("init ServerSocket->{} , error-{} .", this, e.getMessage());
        }
    }

    /**
     * init interacted peers of this peer for each request
     * @return
     */
    public List<Peer> getInterPeers() {

        return interPeers;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", interPeers=" + interPeers +
                ", serverSocket=" + serverSocket +
                '}';
    }
}
