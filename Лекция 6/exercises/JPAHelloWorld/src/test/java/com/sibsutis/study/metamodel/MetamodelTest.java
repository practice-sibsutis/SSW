package com.sibsutis.study.metamodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

class MetamodelTest {
    private static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("ch03.metamodel");
    }

    @Test
    public void accessDynamicMetamodel() {

        Metamodel metamodel = emf.getMetamodel();
        Set<ManagedType<?>> managedTypes = metamodel.getManagedTypes();
        ManagedType<?> itemType = managedTypes.iterator().next();

        assertAll(() -> assertEquals(1, managedTypes.size()),
                () -> assertEquals(
                        Type.PersistenceType.ENTITY,
                        itemType.getPersistenceType()));


        //The attributes of the entity are accessed with a string: the id.
        SingularAttribute<?, ?> idAttribute =
                itemType.getSingularAttribute("id");
        //Check that the id attribute is not optional. This means that it cannot be NULL,
        //since it is the primary key.
        assertFalse(
                idAttribute.isOptional()
        );

        //The name.
        SingularAttribute<?, ?> nameAttribute =
                itemType.getSingularAttribute("name");

        //Check that the name attribute has the String Java type and the basic persistent
        //attribute type.
        assertAll(() -> assertEquals(String.class, nameAttribute.getJavaType()),
                () -> assertEquals(
                        Attribute.PersistentAttributeType.BASIC,
                        nameAttribute.getPersistentAttributeType()
                ));


        //The auctionEnd date. This obviously isn’t type-safe, and if you change the names
        //of the attributes, this code will be broken and obsolete. The strings aren’t automatically
        //included in the refactoring operations of your IDE.
        SingularAttribute<?, ?> auctionEndAttribute =
                itemType.getSingularAttribute("auctionEnd");
        //Check that the auctionEnd attribute has the Date Java type and that it is not a collection
        //or an association.
        assertAll(() -> assertEquals(Date.class, auctionEndAttribute.getJavaType()),
                () -> assertFalse(auctionEndAttribute.isCollection()),
                () -> assertFalse(auctionEndAttribute.isAssociation())
        );

    }

    @Test
    public void accessStaticMetamodel() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();

        //The query is the equivalent of select i from Item i. This query returns all items
        //in the database, and there are two in this case. If you want to restrict this result and
        //only return items with a particular name, you will have to use a like expression,
        //comparing the name attribute of each item with the pattern set in a parameter.
        CriteriaQuery<Item> query = cb.createQuery(Item.class);

        Root<Item> fromItem = query.from(Item.class);
        query.select(fromItem);
        List<Item> items = em.createQuery(query).getResultList();

        assertEquals(2, items.size());
    }

    @Test
    public void testItemsPattern() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        Path<String> namePath = fromItem.get("name");
        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));

        //The query is the equivalent of select i from Item i where i.name like :pattern.
        //Notice that the namePath lookup requires the name string. This is where the typesafety
        //of the criteria query breaks down. You can rename the Item entity class with
        //your IDE’s refactoring tools, and the query will still work. But as soon as you touch
        //the Item#name property, manual adjustments are necessary. Luckily, you’ll catch
        //this when the test fails.
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
        assertAll(() -> assertEquals(1, items.size()),
                () -> assertEquals("Item 1", items.iterator().next().getName()));
    }

    @Test
    public void testItemsPatternWithGeneratedClass() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        Path<String> namePath = fromItem.get(Item_.name);
        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
        assertAll(() -> assertEquals(1, items.size()),
                () -> assertEquals("Item 1", items.iterator().next().getName()));
    }

    private void deleteItems(EntityManager em) {
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Item> query = cb.createCriteriaDelete(Item.class);
        query.from(Item.class);
        em.createQuery(query).executeUpdate();
        em.getTransaction().commit();
    }

    private void persistItems(EntityManager em) {
        em.getTransaction().begin();
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setAuctionEnd(tomorrow());

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setAuctionEnd(tomorrow());

        em.persist(item1);
        em.persist(item2);
        em.getTransaction().commit();
    }

    private Date tomorrow() {
        return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }
}