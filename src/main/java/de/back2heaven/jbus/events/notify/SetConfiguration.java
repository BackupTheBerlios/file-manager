package de.back2heaven.jbus.events.notify;

import de.back2heaven.jbus.AbstractValueConverter;
import de.back2heaven.jbus.NoRemoveIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import de.back2heaven.jbus.events.Notify;
import java.util.Map;

public class SetConfiguration extends AbstractValueConverter implements Notify, Iterable<Map.Entry<String, Object>> {

    private static final long serialVersionUID = 4996420653734515264L;
    private Map<String, Object> map = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<>();

    public SetConfiguration() {
        super();
    }

    public SetConfiguration(String key, Object value) {
        this();
        add(key, value);
    }
    
    public boolean has(String key){
        return keys.contains(key);
    }

    public final void add(String key, Object value) {
        map.put(key, value);
        keys.add(key);
    }

    public String[] getKeys() {
        return keys.toArray(new String[0]);
    }

    @Override
    public Object get(String key) {
        return map.get(key);
    }

    @Override
    public Iterator<Entry<String, Object>> iterator() {
        return new NoRemoveIterator<>(map.entrySet().iterator());
    }
}
