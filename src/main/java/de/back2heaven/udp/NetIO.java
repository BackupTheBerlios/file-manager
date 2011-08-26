package de.back2heaven.udp;

import de.back2heaven.jbus.JBus;
import de.back2heaven.jbus.JavaBusService;
import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;
import de.back2heaven.jbus.events.Event;
import de.back2heaven.jbus.events.callback.GetConfiguration;
import de.back2heaven.jbus.events.notify.SetConfiguration;

public class NetIO implements JavaBusService, CallbackListener {

    private JBus bus;
    private GetConfiguration get = new GetConfiguration(this, "netio.database.file", "netio.database.types");

    @Override
    public void setBus(JBus bus) {
        this.bus = bus;
    }

    // Tabelle
    // IP | PORT | Kennung (wenn bekannt)
    // Kennung | KeepAlive (boolean) | LastMessage (Date)
    // Kennung | MessageQueue (DataCache) | WindowID | MSGID (je nach fenster
    // immer 0-X) | ACK (bestätigt?)
    // Kennung | LogMessage
    // ....
    @Override
    public void run() {
        // setup table
        setupTable();
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setupTable() {
        bus.notifyObservers(get);
    }

    @Override
    public void callback(Callback question, Event answer) {
        // cfg for xy
        System.out.println("???????");
        if (question == get && answer instanceof SetConfiguration) {
            SetConfiguration set = (SetConfiguration) answer;

            for (String k : set.getKeys()) {
                System.out.println(k + ":\t" + set.get(k));
            }

        }

    }
}
