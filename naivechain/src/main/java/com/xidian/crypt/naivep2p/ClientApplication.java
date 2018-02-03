package com.xidian.crypt.naivep2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author LvLiuWei
 * @date 2018/2/2.
 */
public class ClientApplication {

    public static void main(String[] args) throws IOException {
        new ClientApplication().sendMsg();
    }

    private void sendMsg() throws IOException {
        MulticastSocket clientSocket = new MulticastSocket(8889);
        clientSocket.joinGroup(InetAddress.getByName("228.5.6.8"));

        String msg = "hello p2p";
        DatagramPacket sendPackage = new DatagramPacket(
                msg.getBytes(),
                msg.length(),
                InetAddress.getByName("228.5.6.8"),
                8889
        );
        clientSocket.send(sendPackage);
    }
}
