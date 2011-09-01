/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.udp;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;
import de.back2heaven.jbus.events.Event;
import de.back2heaven.jbus.events.callback.GetConfiguration;
import de.back2heaven.jbus.events.notify.SetConfiguration;
import de.back2heaven.pattern.AbstractObservable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Dwerg-McAce
 */
public class StunClient extends AbstractObservable implements JavaService, CallbackListener {

    private final GetConfiguration getCon = new GetConfiguration(this, "stun.port", "stun.intervall", "stun.gateway");
    private DatagramSocket dataSocket;
    private int waitIntervall;
    private int port;
    private InetAddress gateway;

    @Override
    public void run() {
        notifyObservers(getCon);
        System.out.println("basdasdfasdf");
        final StunMessage message = new StunMessage(dataSocket.getLocalAddress(), dataSocket.getLocalPort());
        while (!dataSocket.isClosed()) {
            System.out.println("SENDE request " + gateway + " -- " + port);
            
            DatagramPacket data = new DatagramPacket(message.getBytes(), message.getSize(), gateway, port);
            try {
                dataSocket.send(data);
                
                byte[] antwort = new byte[message.getSize()*2];
                DatagramPacket antwortPacket = new DatagramPacket(antwort, antwort.length);
                dataSocket.receive(antwortPacket);
                StunMessage antwortEx = new StunMessage(antwortPacket.getData());
                System.out.println(antwortEx.getAddress() + " :: "+ antwortEx.getPort());
                Thread.sleep(waitIntervall);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        dataSocket.close();
        waitIntervall = -1;
    }

    @Override
    public void callback(Callback question, Event answer) {
        if (answer instanceof SetConfiguration) {
            SetConfiguration set = (SetConfiguration) answer;
            waitIntervall = Integer.parseInt((String) set.get("stun.intervall"));
            port = Integer.parseInt((String) set.get("stun.port"));

            if (waitIntervall < 1) {
                throw new RuntimeException("unsupported Intervall");
            }
            try {
                gateway = InetAddress.getByName((String) set.get("stun.gateway"));
                if (dataSocket == null) {
                    dataSocket = new DatagramSocket();
                    dataSocket.setSoTimeout(Math.max(waitIntervall,10000));
                    dataSocket.setReuseAddress(true);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
