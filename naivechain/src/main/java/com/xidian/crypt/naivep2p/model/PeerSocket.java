package com.xidian.crypt.naivep2p.model;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

/**
 * @author LvLiuWei
 * @date 2018/2/2.
 */
@Data
public class PeerSocket {
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public PeerSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
    }

    public PeerSocket(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    public void close() {

    }

    public String read() {
        return null;
    }



}
