package de.back2heaven.jbus.agent;

import de.back2heaven.gui.AddTab;
import de.back2heaven.gui.RemoveTab;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import de.back2heaven.jbus.JBus;
import de.back2heaven.jbus.JavaBusService;
import javax.swing.JPanel;

public final class SwingAgent implements JavaBusService, ActionListener {

    private JBus bus;
    private SystemTray tray;

    @Override
    public void run() {
        if (!java.awt.SystemTray.isSupported()) {
            throw new RuntimeException("SystemTray not supported");
        }
        tray = new SystemTray(this);
        try {
            tray.toTray();
        } catch (IOException e) {
            bus.stop();
        }
    }

    @Override
    public void setBus(JBus bus) {
        this.bus = bus;
    }

    @Override
    public void close() {
        tray.remove();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // exit the bus
        switch (e.getActionCommand()) {
            case "about":
                System.out.print("AAA");
                AddTab t = new AddTab("SomeTab", new JPanel());
                bus.notifyObservers(t);
                break;
            case "exit":
                bus.stop();
                break;
            case "check for updates":
                RemoveTab xt = new RemoveTab("SomeTab");
                bus.notifyObservers(xt);
                System.out.println("help!!");
                break;
            case "main":
                try {
                    bus.load("de.back2heaven.gui.Main");
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
                break;

            default:
                System.out.println(e.getActionCommand());
        }
    }
}
