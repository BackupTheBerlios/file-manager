package de.back2heaven.udp;

import de.back2heaven.jbus.JBus;
import de.back2heaven.jbus.JavaBusService;
import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;
import de.back2heaven.jbus.events.Event;
import de.back2heaven.jbus.events.callback.GetConfiguration;
import de.back2heaven.jbus.events.notify.SetConfiguration;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StunService implements JavaBusService, CallbackListener {

    private final GetConfiguration get = new GetConfiguration(this, "stun.port");
    private DatagramSocket udp;
    private JBus bus;

    @Override
    public void run() {
        // service port?
        bus.notifyObservers(get);
        // w8 vor messages

        while (!udp.isClosed()) {
            byte[] bytes = new byte[512];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            try {
                System.out.println("ONLINE");
                udp.receive(packet);
                // nachricht bekommen
                StunMessage stunMessage = new StunMessage(packet.getData());
                System.out.println("FOUND: " + stunMessage.getAddress()  +" :: " + stunMessage.getPort());
                StunMessage extMessage = new StunMessage(packet.getAddress(), packet.getPort(), stunMessage);
                DatagramPacket message = new DatagramPacket(extMessage.getBytes(), extMessage.getSize(), packet.getAddress(), packet.getPort());
                udp.send(message);
                System.out.println("Send MEssage... ");
            } catch (IOException ex) {
               // Logger.getLogger(StunService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // 
    }

    @Override
    public void close() throws Exception {
        udp.close();
    }

    @Override
    public void callback(Callback question, Event answer) {
        if (answer instanceof SetConfiguration) {
            SetConfiguration set = (SetConfiguration) answer;
            int port = Integer.parseInt((String) set.get("stun.port"));
            try {
                if (udp == null) {
                    udp = new DatagramSocket(port);
                }
                udp.setSoTimeout(10000);
            } catch (SocketException ex) {
                Logger.getLogger(StunService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void setBus(JBus bus) {
        this.bus = bus;
    }
}
