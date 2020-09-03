package ru.job4j.tracker;

public class DeleteItemAction implements UserAction {
    @Override
    public String name() {
        return "=== Delete item ==========";
    }

    @Override
    public boolean execute(Input input, MemTracker memTracker) {
        String delId = input.askStr("Enter ID: ");
        boolean result = memTracker.delete(delId);
        if (result) {
            System.out.println("OK");
        } else {
            System.out.println("Item not found");
        }
        return true;
    }
}
