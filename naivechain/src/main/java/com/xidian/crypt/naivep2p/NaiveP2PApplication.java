package com.xidian.crypt.naivep2p;

import com.xidian.crypt.naivep2p.model.PeerSocket;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author LvLiuWei
 * @date 2018/2/2.
 */
@Slf4j
public class NaiveP2PApplication {
    public static void main(String[] args) throws IOException {

//        PeerSocket socket1 = new PeerSocket("localhost", 9000);
//        PeerSocket socket2 = new PeerSocket("localhost", 9001);
//
        new NaiveP2PApplication().initMulticastSocket();

    }


    public void initMulticastSocket() throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket(8888);
        InetAddress group = InetAddress.getByName("228.5.6.7");
        multicastSocket.joinGroup(group);

        String msg = "hello multicast";
        DatagramPacket packet = new DatagramPacket(msg.getBytes(),
                msg.length(),
                group,
                8888);
        multicastSocket.send(packet);

        MulticastSocket multicastSocket1 = new MulticastSocket(8889);
        InetAddress group1 = InetAddress.getByName("228.5.6.8");
        multicastSocket1.joinGroup(group1);

        byte[] recBuffer = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recBuffer, recBuffer.length);
        multicastSocket1.receive(recvPacket);
        String result1 = new String(recBuffer);
        log.info(result1);

        multicastSocket.receive(recvPacket);
        String result = new String(recBuffer);
        System.out.print(result);
    }

}
