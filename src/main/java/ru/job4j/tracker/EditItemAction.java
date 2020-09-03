package ru.job4j.tracker;

public class EditItemAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit item ============";
    }

    @Override
    public boolean execute(Input input, MemTracker memTracker) {
        String editId = input.askStr("Enter ID: ");
        String newName = input.askStr("Enter new name: ");
        Item newItem = new Item(newName);
        boolean result = memTracker.replace(editId, newItem);
        if (result) {
            System.out.println("OK");
        } else {
            System.out.println("Item not found");
        }
        return true;
    }
}
