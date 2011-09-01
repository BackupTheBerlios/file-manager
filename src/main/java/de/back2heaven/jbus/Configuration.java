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
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class Configuration extends AbstractValueConverter {

    private Map<String, Object> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(String key) {
        return map.get(key);
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public Object remove(String key) {
        return map.remove(key);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        for (Map.Entry<? extends String, ? extends Object> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public void clear() {
        map.clear();
    }

    public Set<String> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    public Collection<Object> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
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
        try (Writer writer = Files.newBufferedWriter(propertiesFile, Charset.defaultCharset())) {
            final Properties p = new Properties();
            for (Entry<String, ?> e : entrySet()) {
                p.put(e.getKey(), e.getValue());
            }
            final Date currentStamp = new Date();
            p.store(writer, String.format("Generated at %s", currentStamp));
        }
    }
}
