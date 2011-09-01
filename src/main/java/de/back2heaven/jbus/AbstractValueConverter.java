/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.jbus;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Jens Kapitza
 */
public abstract class AbstractValueConverter {

    public abstract Object get(String key);

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

    public long getLong(final String key) {
        Object obj = get(key);
        Long i;
        if (obj instanceof Long) {
            i = (Long) obj;
        } else {
            return Long.parseLong(String.valueOf(obj));
        }
        return i.longValue();
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

    public InetAddress getAddress(final String key) {
        try {
            return InetAddress.getByName(getString(key));
        } catch (UnknownHostException ex) {
            return null;
        }
    }
}
