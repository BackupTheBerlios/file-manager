package de.back2heaven.udp;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;
import de.back2heaven.jbus.events.Event;
import de.back2heaven.jbus.events.callback.GetConfiguration;
import de.back2heaven.jbus.events.notify.SetConfiguration;
import de.back2heaven.pattern.AbstractObservable;

public class NetIO extends AbstractObservable implements JavaService, CallbackListener {

    private GetConfiguration get = new GetConfiguration(this, "netio.database.file", "netio.database.types");


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
        notifyObservers(get);
    }

    @Override
    public void callback(Callback question, Event answer) {
        // cfg for xy
        if (answer instanceof SetConfiguration) {
            SetConfiguration set = (SetConfiguration) answer;
            
            for (String k : set.getKeys()) {
                System.out.println(k + ":\t" + set.get(k));
            }

        }

    }
}
