/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.jbus;

import de.back2heaven.jbus.events.CallbackListener;
import de.back2heaven.jbus.events.Event;
import de.back2heaven.jbus.events.Notify;
import de.back2heaven.jbus.events.callback.GetConfiguration;
import de.back2heaven.jbus.events.notify.SetConfiguration;
import de.back2heaven.pattern.Observable;
import de.back2heaven.pattern.Observer;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author Jens Kapitza
 */
public class AutoReloadConfiguration extends Configuration implements JavaBusService, Observer {

    private JBus bus;
    private Path path;
    private WatchService watcher;
    private Map<String, Collection<CallbackListener>> listener = new ConcurrentHashMap<>();

    @Override
    public void setBus(JBus bus) {
        this.bus = bus;
    }

    public AutoReloadConfiguration() {
        this("data/jbus.properties");
    }

    public AutoReloadConfiguration(String file) {
        path = Paths.get(file);
    }

    @Override
    public void run() {
        boolean reload = false;
        boolean save = false;
        try {
            read(path); // initial read
            watcher = FileSystems.getDefault().newWatchService();
            path.getParent().register(watcher,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path a = path.getFileName();
                    Path b = (Path) event.context();

                    if (a.equals(b)) {
                        if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) {
                            // reload kontext if path is the same
                            reload = true;
                        }
                        if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) {
                            // bei delete neu schreiben
                            save = true;
                        }
                    }
                }
                key.reset();
                if (reload) {
                    try {
                        read(path);
                    } catch (NoSuchFileException e) {
                        // ignore
                    }
                    reload = false;
                }
                if (save) {
                    save(path);
                    save = false;
                }
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            // ignore just schutdown the service
        }

        watcher = null;

    }

    @Override
    public Object put(String key, Object value) {

        Object v = super.put(key, value);
        if (v == null || !v.equals(value)) {
            // listener benachrichtigen

            Collection<CallbackListener> set = listener.get(key);
            if (set != null) {
                SetConfiguration setEvent = new SetConfiguration(key, value);
                for (CallbackListener l : set) {
                    l.callback(null, setEvent);
                }
            }
        }
        return v;
    }

    @Override
    public void close() throws Exception {
        watcher.close();
    }

    @Override
    public boolean accept(Object obj) {
        return obj instanceof Notify || obj instanceof Event;
    }

    @Override
    public void update(Observable from, Object args) {
        System.out.println(args.getClass());
        System.out.println(args);

        if (args instanceof GetConfiguration) {
            GetConfiguration get = (GetConfiguration) args;

            CallbackListener l = get.getListener();
            SetConfiguration setEvent = new SetConfiguration();
            for (String k : get.getKeys()) {
                if (!listener.containsKey(k)) {
                    listener.put(k, new CopyOnWriteArraySet<CallbackListener>());
                }
                Collection<CallbackListener> set = listener.get(k);

                if (!set.contains(l)) {
                    set.add(l);
                }

                setEvent.add(k, get(k));

            }
            l.callback(get, setEvent);
        }
    }
}
