/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.test.jpa;

import de.back2heaven.jpa.OpenJPA;
import de.back2heaven.udp.netio.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.Test;

/**
 *
 * @author Jens Kapitza
 */
public class JPATest {

    @Test
    public void doJPATest() {

//
//        Map<String, Object> configOverrides = new HashMap<String, Object>();
//        configOverrides.put("hibernate.hbm2ddl.auto", "create-drop");
//        EntityManagerFactory programmaticEmf =
//                Persistence.createEntityManagerFactory("de.back2heaven.jbus.netio", configOverrides);
//

        EntityManagerFactory fac = OpenJPA.getFactory("data/db", User.class);


        EntityManager e = fac.createEntityManager();

        EntityTransaction t = e.getTransaction();

        t.begin();

        for (int i = 0; i < 10; i++) {
            User u = new User("NewName " + i);

            e.persist(u);
        }



        t.commit();


        t.begin();

        for (Object o : e.createQuery("SELECT u FROM User u").getResultList()) {
            System.out.println("USER: " + ((User) o).getNetID());
        }

        t.rollback();

    }
}
