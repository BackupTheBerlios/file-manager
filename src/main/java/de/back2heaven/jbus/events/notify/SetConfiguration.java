package de.back2heaven.jbus.events.notify;

import java.util.ArrayList;
import java.util.HashMap;

import de.back2heaven.jbus.events.Notify;

public class SetConfiguration implements Notify {

	private static final long serialVersionUID = 4996420653734515264L;
	private HashMap<String, String> map = new HashMap<>();
	private ArrayList<String> keys = new ArrayList<>();

	public SetConfiguration() {
		super();
	}

	public SetConfiguration(String key, String value) {
		this();
		add(key, value);
	}

	public void add(String key, String value) {
		map.put(key, value);
	}

	public String[] getKeys() {
		return keys.toArray(new String[0]);
	}

	public String get(String key) {
		return map.get(key);
	}

}
