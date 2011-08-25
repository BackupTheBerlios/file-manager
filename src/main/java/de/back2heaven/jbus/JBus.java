package de.back2heaven.jbus;

import de.back2heaven.jbus.events.StateChangeHandler;
import de.back2heaven.jbus.events.StateEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.back2heaven.jbus.events.notify.ServiceStart;
import de.back2heaven.jbus.events.notify.ServiceStop;
import de.back2heaven.pattern.Observable;
import de.back2heaven.pattern.Observer;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public final class JBus implements Observer, Observable {

    private HashMap<String, Class<?>> serviceLoader = new HashMap<>();
    private HashMap<String, JavaService> services = new HashMap<>();
    private List<Observer> observers = new Vector<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private List<AutoCloseable> closable = new Vector<>();

    public void stop() {
        // shutdown all services
        exec.shutdown();
        for (AutoCloseable ac : closable) {

            try {
                ac.close();
            } catch (Exception e) {
                // ignore exception
            }

            if (ac instanceof JavaBusService) {
                JavaBusService bservice = (JavaBusService) ac;
                bservice.setBus(null);
            }
            if (ac instanceof JavaService) {
                JavaService s = (JavaService) ac;
                ServiceStop stop = new ServiceStop(s);
                notifyObservers(stop);
            }
        }
        // freigeben der listen
        closable = null;
        services = null;
    }

    public void execute(Runnable service) {
        if (service instanceof AutoCloseable) {
            closable.add((AutoCloseable) service);
        }
        exec.execute(service);

    }

    public void load(String javaService) throws InstantiationException {
        if (exec.isShutdown() || exec.isTerminated()) {
            throw new InstantiationException("Bus shutdown called");
        }
        Class<?> clazz = serviceLoader.get(javaService);
        if (clazz == null) {
            try {
                clazz = Class.forName(javaService);
            } catch (ClassNotFoundException e) {
                throw new InstantiationException("ServiceClass load fail.");
            }
            serviceLoader.put(javaService, clazz);
        } else {
            if (services.containsKey(javaService)) {
                // no restart
                throw new InstantiationException("Service is running.");
            }
        }
        try {
            JavaService service = (JavaService) clazz.newInstance();
            services.put(javaService, service);

            if (service instanceof JavaBusService) {
                JavaBusService xservice = (JavaBusService) service;
                xservice.setBus(this);
            }
            // wenn es ein observer ist dann
            // anmelden
            if (service instanceof Observer) {
                Observer obeservice = (Observer) service;
                this.addObserver(obeservice);
            }

            // wenn es ein observebel ist, dann
            // diesen bus anmelden

            // um daten zu bekommen

            if (service instanceof Observable) {
                Observable observer = (Observable) service;
                observer.addObserver(this); // benachrichtung
            }

            execute(service);
            ServiceStart start = new ServiceStart(service);
            notifyObservers(start);
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Service creation fail.");
        }
    }

    public void unload(String javaService) {
        if (services.containsKey(javaService)) {
            JavaService service = services.get(javaService);
            try {
                service.close();
            } catch (Exception e) {
                // ignore ex while unload
            }
            if (service instanceof JavaBusService) {
                JavaBusService bservice = (JavaBusService) service;
                bservice.setBus(null);
            }
            ServiceStop stop = new ServiceStop(service);
            notifyObservers(stop);
            services.remove(javaService);
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        notifyObservers(this, arg);
    }

    private void callUpdate0(final Observable from, final Object args, final Observer o) {
        if (o.accept(args)) {
            o.update(from, args);
        }
    }

    private void callUpdate(final Observable from, final Object args, final Observer o) {
        if (o instanceof JComponent) {
            final Runnable r1 = new Runnable() {

                @Override
                public void run() {
                    callUpdate0(from, args, o);
                }
            };
            // ensure call is in swing thread
            SwingUtilities.invokeLater(r1);
        } else {
            callUpdate0(from, args, o);
        }
    }

    private void callServiceHandler0(final StateChangeHandler h, final Object args) {
        if (args instanceof ServiceStart) {
            h.onStart((ServiceStart) args);
        }
        if (args instanceof ServiceStop) {
            h.onStop((ServiceStop) args);
        }
    }
    
    public void notifyObservers(final Observable from, final Object args) {
        for (final Observer o : observers) {
            if (o instanceof StateChangeHandler && args instanceof StateEvent) {
                final StateChangeHandler h = (StateChangeHandler) o;
                if (o instanceof JComponent) {
                    final Runnable r1 = new Runnable() {

                        @Override
                        public void run() {
                            callServiceHandler0(h, args);
                        }
                    };
                    // ensure call is in swing thread
                    SwingUtilities.invokeLater(r1);
                } else {
                    callServiceHandler0(h, args);
                }
            } else {
                callUpdate(from, args, o);
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public boolean accept(Object obj) {
        // alles erlauben
        return true;
    }

    @Override
    public void update(Observable from, Object args) {
        if (accept(args)) {
            notifyObservers(from, args);
        }
    }

    public void unload(Class aThis) {
        unload(aThis.getName());
    }

    public void unload(Object aThis) {
        unload(aThis.getClass());
    }
}
