package de.back2heaven.jbus.events.notify;

import de.back2heaven.jbus.events.Event;
import java.util.ArrayList;
import java.util.HashMap;

import de.back2heaven.jbus.events.Notify;

public class SetConfiguration implements Notify,Event {

	private static final long serialVersionUID = 4996420653734515264L;
	private HashMap<String, Object> map = new HashMap<>();
	private ArrayList<String> keys = new ArrayList<>();

	public SetConfiguration() {
		super();
	}

	public SetConfiguration(String key, Object value) {
		this();
		add(key, value);
	}

	public final void add(String key, Object value) {
		map.put(key, value);
                keys.add(key);
	}

	public String[] getKeys() {
		return keys.toArray(new String[0]);
	}
        
	public Object get(String key) {
		return map.get(key);
	}

}
