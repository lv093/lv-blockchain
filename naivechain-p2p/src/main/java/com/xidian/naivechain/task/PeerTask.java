package com.xidian.naivechain.task;

import com.xidian.naivechain.model.Peer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LvLiuWei
 * @date 2018/2/3.
 * PeerTask Thread listening for receiving message from adjacent Peer
 */
public class PeerTask implements Runnable {

    private Peer peer;

    public PeerTask(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket server = peer.getServerSocket();
                Socket client = server.accept();
                Reader reader = new InputStreamReader(client.getInputStream());

                char[] recChar = new char[1024];
                int len;
                StringBuilder builder = new StringBuilder();
                while ((len = reader.read(recChar)) != -1) {
                    builder.append(recChar, 0, len);
                    System.out.println(peer + " receive message: " + builder.toString());
                }
            } catch (IOException e) {
                e.getMessage();
            }

        }
    }
}
