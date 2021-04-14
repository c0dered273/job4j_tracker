package ru.job4j.tracker;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HbmTracker.class);
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
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in add() with Item={}", item);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in replace() with Item={}", item);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in delete() with Item.id={}", id);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return true;
    }

    @Override
    public List<Item> findAll() {
        List result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Item item = new Item();
            result = session.createQuery("from ru.job4j.tracker.Item").list();
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in findAll()");
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Item item = new Item();
            result = session.createQuery("from ru.job4j.tracker.Item i where i.name = :name")
                    .setParameter("name", key).list();
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in findByName() with Item.name={}", key);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return result;
    }

    @Override
    public Item findById(Integer id) {
        Item result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            result = session.get(Item.class, id);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("Error in findById() with Item.id={}", id);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new HibernateException(e);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
