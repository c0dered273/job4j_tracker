package ru.job4j.tracker;

import java.util.List;

public class TrackerStaticFieldSingleton {

    private static TrackerStaticFieldSingleton instance;
    private static final MemTracker MEM_TRACKER = new MemTracker();

    private TrackerStaticFieldSingleton() {
    }

    public static TrackerStaticFieldSingleton getInstance() {
        if (instance == null) {
            instance = new TrackerStaticFieldSingleton();
        }
        return  instance;
    }

    public Item add(Item item) {
        return MEM_TRACKER.add(item);
    }

    public boolean replace(String id, Item item) {
        return MEM_TRACKER.replace(id, item);
    }

    public boolean delete(String id) {
        return MEM_TRACKER.delete(id);
    }

    public List<Item> findAll() {
        return MEM_TRACKER.findAll();
    }

    public List<Item> findByName(String key) {
        return MEM_TRACKER.findByName(key);
    }

    public Item findById(String id) {
        return MEM_TRACKER.findById(id);
    }
}
