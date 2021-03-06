/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.apache.openjpa.persistence.OpenJPAPersistence;

/**
 *
 * @author Jens Kapitza
 */
public class OpenJPA {

    private OpenJPA() {
    }

    public static EntityManagerFactory getFactory(String db, Class<?>... typeClazz) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("openjpa.ConnectionURL", "jdbc:derby:" + db + ";create=true");
        map.put("openjpa.InverseManager", "true");

        map.put("openjpa.ConnectionDriverName", "org.apache.derby.jdbc.EmbeddedDriver");
        map.put("openjpa.ConnectionUserName", "user");
        map.put("openjpa.ConnectionPassword", "user");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.Log", "DefaultLevel=WARN, Runtime=INFO, Tool=INFO, SQL=TRACE");
        map.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(SchemaAction='add,deleteTableContents',ForeignKeys=true)");

        // find all classes to registrate them
        List<Class<?>> types = new ArrayList<Class<?>>(Arrays.asList(typeClazz));

        if (!types.isEmpty()) {
            StringBuilder buf = new StringBuilder();
            for (Class<?> c : types) {
                if (buf.length() > 0) {
                    buf.append(";");
                }
                buf.append(c.getName());
            }
            // <class>de.back2heaven.Cite</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()
                    + ")");
        }
        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
}
