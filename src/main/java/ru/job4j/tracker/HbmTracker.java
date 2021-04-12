package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public List<Item> findAll() {
        List rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            rsl = session.createQuery("from ru.job4j.tracker.Item").list();
            session.getTransaction().commit();
        }
        return rsl;
    }

    @Override
    public List<Item> findByName(String key) {
        List rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            rsl = session.createQuery("from ru.job4j.tracker.Item i where i.name = :name")
                    .setParameter("name", key).list();
            session.getTransaction().commit();
        }
        return rsl;
    }

    @Override
    public Item findById(Integer id) {
        Item rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            rsl = session.get(Item.class, id);
            session.getTransaction().commit();
        }
        return rsl;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
