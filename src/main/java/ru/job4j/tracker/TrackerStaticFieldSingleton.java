package ru.job4j.tracker;

import java.util.List;

public class TrackerStaticFieldSingleton {

    private static TrackerStaticFieldSingleton instance;
    private static final Tracker TRACKER = new Tracker();

    private TrackerStaticFieldSingleton() {
    }

    public static TrackerStaticFieldSingleton getInstance() {
        if (instance == null) {
            instance = new TrackerStaticFieldSingleton();
        }
        return  instance;
    }

    public Item add(Item item) {
        return TRACKER.add(item);
    }

    public boolean replace(String id, Item item) {
        return TRACKER.replace(id, item);
    }

    public boolean delete(String id) {
        return TRACKER.delete(id);
    }

    public List<Item> findAll() {
        return TRACKER.findAll();
    }

    public List<Item> findByName(String key) {
        return TRACKER.findByName(key);
    }

    public Item findById(String id) {
        return TRACKER.findById(id);
    }
}
