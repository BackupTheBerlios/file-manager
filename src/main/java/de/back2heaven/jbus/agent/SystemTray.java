package de.back2heaven.jbus.agent;

import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.net.URL;

public final class SystemTray {

    private final PopupMenu popup = new PopupMenu();
    private final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
    private TrayIcon trayIcon;

    public SystemTray(ActionListener li) {

        // Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        Menu displayMenu = new Menu("Display");

        MenuItem log = new MenuItem("Log Window");
        MenuItem cfg = new MenuItem("Configuration Window");
        MenuItem app = new MenuItem("AppStarter Window");

        MenuItem update = new MenuItem("Check for Updates");
        MenuItem main1 = new MenuItem("Main");
        MenuItem exitItem = new MenuItem("Exit");

        addActionListener(li, main1, aboutItem, displayMenu, exitItem, update,
                app, cfg, log);

        // Add components to pop-up menu
        popup.add(aboutItem);
        popup.add(main1);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(log);
        displayMenu.add(cfg);
        displayMenu.add(app);
        popup.addSeparator();
        popup.add(update);
        popup.add(exitItem);

    }

    private void addActionListener(ActionListener li, MenuItem... items) {
        for (MenuItem it : items) {
            it.setActionCommand(it.getLabel().toLowerCase());
            it.addActionListener(li);
        }
    }

    public void toTray() throws IOException {
        if (trayIcon == null) {
            URL u = getClass().getResource("/tray.jpg");
            System.out.print(u);
            trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(u));
        }
        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new IOException("TrayIcon could not be added.");
        }
    }

    public void remove() {
        tray.remove(trayIcon);
    }
}
