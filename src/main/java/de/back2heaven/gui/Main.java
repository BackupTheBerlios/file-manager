package de.back2heaven.gui;

import de.back2heaven.jbus.JBus;
import de.back2heaven.jbus.JavaBusService;
import java.awt.event.WindowEvent;

import de.back2heaven.jbus.JavaService;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

public class Main extends WindowAdapter implements JavaService, JavaBusService {

    private JFrame mainView;
    private JBus bus;

    @Override
    public void setBus(JBus bus) {
        this.bus = bus;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (bus != null) {
            bus.unload(this);
        }
    }

    @Override
    public void close() throws Exception {
        WindowEvent event = new WindowEvent(mainView, WindowEvent.WINDOW_CLOSING);
        mainView.dispatchEvent(event);
    }

    @Override
    public void run() {
        mainView = new JFrame("JBUS");
        MainTabPane tab = new MainTabPane();
        bus.addObserver(tab);
        mainView.add(tab, BorderLayout.CENTER);
        mainView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mainView.addWindowListener(this);
        mainView.pack();
        mainView.setVisible(true);
    }
}