package ru.job4j.tracker;

public class CreateAction implements UserAction {
    @Override
    public String name() {
        return "=== Create a new Item ====";
    }

    @Override
    public boolean execute(Input input, MemTracker memTracker) {
        String name = input.askStr("Enter name: ");
        memTracker.add(new Item(name));
        return true;
    }
}
