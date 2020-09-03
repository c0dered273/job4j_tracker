package ru.job4j.tracker;

import java.util.List;

public class FindByNameAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by name ====";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        String itemName = input.askStr("Enter item name: ");
        List<Item> result = tracker.findByName(itemName);
        if (!result.isEmpty()) {
            for (Item item : result) {
                System.out.println("ID: " + item.getId() + "    " + "Name: " + item.getName());
            }
        } else {
            System.out.println("Items not found");
        }
        return true;
    }
}
