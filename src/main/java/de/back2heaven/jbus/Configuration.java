package de.back2heaven.jbus;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Configuration implements Map<String, Object> {
    
    private HashMap<String, Object> map = new HashMap<>();
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }
    
    @Override
    public Object get(Object key) {
        return map.get(key);
    }
    
    @Override
    public Object put(String key, Object value) {
        // TODO EVENT ?
        return map.put(key, value);
    }
    
    @Override
    public Object remove(Object key) {
        // EVENT ?
        return map.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        // EVENT ?
        map.putAll(m);
    }
    
    @Override
    public void clear() {
        // EVENT ?
        map.clear();
    }
    
    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }
    
    @Override
    public Collection<Object> values() {
        return Collections.unmodifiableCollection(map.values());
    }
    
    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }

    // -----------------------------------------------------------
    // get methods
    public int getInt(final String key) {
        Object obj = get(key);
        Integer i;
        if (obj instanceof Integer) {
            i = (Integer) obj;
        } else {
            return Integer.parseInt(String.valueOf(obj));
        }
        return i.intValue();
    }
    
    public double getDouble(final String key) {
        Object obj = get(key);
        Double i;
        if (obj instanceof Double) {
            i = (Double) obj;
        } else {
            return Double.parseDouble(String.valueOf(obj));
        }
        return i.doubleValue();
    }
    
    public String getString(final String key) {
        Object obj = get(key);
        if (obj instanceof String) {
            return (String) obj;
        }
        return String.valueOf(obj);
    }
    
    public boolean isSet(final String key) {
        Object obj = get(key);
        if (obj instanceof Boolean) {
            Boolean b = (Boolean) obj;
            return b.booleanValue();
        }
        return Boolean.parseBoolean(String.valueOf(obj));
    }

    // -----------------------------------------------------------
    // read properties file
    public void read(final String propertiesFile) throws IOException {
        read(Paths.get(propertiesFile));
    }
    public void read(final Path propertiesFile) throws IOException {
        try (Reader reader = Files.newBufferedReader(propertiesFile, Charset.defaultCharset())) {
            final Properties p = new Properties();
            p.load(reader);
            for (Entry<?, ?> e : p.entrySet()) {
                put(String.valueOf(e.getKey()), e.getValue());
            }
        }
    }
    public void save(final String propertiesFile) throws IOException {
        save(Paths.get(propertiesFile));
    }
    
    public void save(final Path propertiesFile) throws IOException {
        try (Writer writer = Files. newBufferedWriter(propertiesFile, Charset.defaultCharset())) {
            final Properties p = new Properties();
            for (Entry<String, ?> e : entrySet()) {
                p.put(e.getKey(), e.getValue());
            }
            final Date currentStamp = new Date();
            p.store(writer, String.format("Generated at %s", currentStamp));
        }
    }
}
