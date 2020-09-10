package ru.job4j.tracker;

import java.util.List;

public class FindAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Show all items =======";
    }

    @Override
    public boolean execute(Input input, Store memTracker) {
        List<Item> items = memTracker.findAll();
        for (Item item : items) {
            System.out.println("ID: " + item.getId() + " "
                    + "Name: " + item.getName() + " "
                    + "Description: " + item.getDescription());
        }
        return true;
    }
}
