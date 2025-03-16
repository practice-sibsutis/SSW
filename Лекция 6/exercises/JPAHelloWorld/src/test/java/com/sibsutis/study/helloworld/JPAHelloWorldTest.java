package com.sibsutis.study.helloworld;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JPAHelloWorldTest {
    @Test
    public void storeLoadMessage() {
        /*
        First we need an EntityManagerFactory to talk to the database. This API
        represents the persistence unit, and most applications have one EntityManager-
        Factory for one configured persistence unit. Once it starts, the application should
        create the EntityManagerFactory; the factory is thread-safe, and all code in the
        application that accesses the database should share it.
         */
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("helloworldjpa");

        try {
            /*
            Begin a new session with the database by creating an EntityManager. This is the
            context for all persistence operations.
             */
            EntityManager em = emf.createEntityManager();

            /*
            Get access to the standard transaction API, and begin a transaction on this thread
            of execution.
             */
            em.getTransaction().begin();

            /*
            Create a new instance of the mapped domain model class Message, and set its text property.
             */
            Message message = new Message();
            message.setText("Hello World!");

            /*
            Enlist the transient instance with the persistence context; we make it persistent.
            Hibernate now knows that we wish to store that data, but it doesn't necessarily call
            the database immediately.
             */
            em.persist(message);

            /*
            Commit the transaction. Hibernate automatically checks the persistence context
            and executes the necessary SQL INSERT statement. To help you understand how
            Hibernate works, we show the automatically generated and executed SQL statements
            in source code comments when they occur. Hibernate inserts a row in the
            MESSAGE table, with an automatically generated value for the ID primary key column,
            and the TEXT value.
             */
            em.getTransaction().commit();
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')

            /*
            Every interaction with the database should occur within transaction boundaries,
            even if we’re only reading data, so we start a new transaction. Any potential failure
            appearing from now on will not affect the previously committed transaction.
             */
            em.getTransaction().begin();

            /*
            Execute a query to retrieve all instances of Message from the database.
             */
            List<Message> messages =
                    em.createQuery("select m from Message m", Message.class).getResultList();
            //SELECT * from MESSAGE

            /*
            We can change the value of a property. Hibernate detects this automatically
            because the loaded Message is still attached to the persistence context it was
            loaded in.
             */
            messages.get(messages.size() - 1).setText("Hello World from JPA!");

            /*
            On commit, Hibernate checks the persistence context for dirty state, and it executes
            the SQL UPDATE automatically to synchronize in-memory objects with the
            database state.
             */
            em.getTransaction().commit();
            //UPDATE MESSAGE set TEXT = 'Hello World from JPA!' where ID = 1

            /*
            Check the size of the list of messages retrieved from the database.
             */
            assertAll(
                    () -> assertEquals(1, messages.size()),
                    /*
                    Check that the message we persisted is in the database. We use the JUnit 5 assert-
                    All method, which always checks all the assertions that are passed to it, even if
                    some of them fail. The two assertions that we verify are conceptually related.
                     */
                    () -> assertEquals("Hello World from JPA!", messages.get(0).getText())
            );

            /*
            We created an EntityManager, so we must close it.
             */
            em.close();

        } finally {
            /*
            We created an EntityManagerFactory, so we must close it.
             */
            emf.close();
        }
    }
}