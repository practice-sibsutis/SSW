package com.sibsutis.study.helloworld;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldHibernateToJPATest {

    private static EntityManagerFactory createEntityManagerFactory() {
        //Create a new Hibernate configuration.
        Configuration configuration = new Configuration();
        //Call the configure method, which adds the content of the default hibernate.cfg.xml
        // file to the configuration, and then explicitly add Message as an annotated
        //class.
        configuration.configure().addAnnotatedClass(Message.class);

        //Create a new hash map to be filled in with the existing properties.
        Map<String, String> properties = new HashMap<>();
        //Get all the property names from the Hibernate configuration.
        Enumeration<?> propertyNames = configuration.getProperties().propertyNames();

        //Add the property names one by one to the previously created map.
        while (propertyNames.hasMoreElements()) {
            String element = (String) propertyNames.nextElement();
            properties.put(element, configuration.getProperties().getProperty(element));
        }

        //Return a new EntityManagerFactory, providing to it the helloworldjpa persistence
        //unit name and the previously created map of properties.
        return Persistence.createEntityManagerFactory("helloworldjpa", properties);
    }

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf = createEntityManagerFactory();

        try {

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World from Hibernate to JPA!");

            em.persist(message);

            em.getTransaction().commit();
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World from Hibernate to JPA!')

            List<Message> messages =
                    em.createQuery("select m from Message m", Message.class).getResultList();
            //SELECT * from MESSAGE

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from Hibernate to JPA!", messages.get(0).getText())
            );

            em.close();

        } finally {
            emf.close();
        }
    }

}