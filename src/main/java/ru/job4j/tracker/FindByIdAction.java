package ru.job4j.tracker;

public class FindByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by ID ======";
    }

    @Override
    public boolean execute(Input input, MemTracker memTracker) {
        System.out.print("Enter ID: ");
        String fndId = input.askStr("Enter ID: ");
        Item result = memTracker.findById(fndId);
        if (result != null) {
            System.out.println("ID: " + result.getId() + "    " + "Name: " + result.getName());
        } else {
            System.out.println("Item not found");
        }
        return true;
    }
}
