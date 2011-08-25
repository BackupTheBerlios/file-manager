package de.back2heaven.jbus.events.callback;

import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;

public class GetConfiguration implements Callback {

    private static final long serialVersionUID = 7494974310178839357L;
    private String[] keys;
    private CallbackListener listener;

    public GetConfiguration(CallbackListener listener, String... keys) {
        this.keys = keys;
        this.listener = listener;
    }

    public String[] getKeys() {
        return keys;
    }

    public CallbackListener getListener() {
        return listener;
    }
}
