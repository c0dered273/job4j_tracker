package ru.job4j.tracker;

public class FindByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by ID ======";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer fndId = input.askInt("Enter ID: ");
        Item result = tracker.findById(fndId);
        if (result != null) {
            System.out.println("ID: " + result.getId() + " "
                    + "Name: " + result.getName() + " "
                    + "Description: " + result.getDescription());
        } else {
            System.out.println("Item not found");
        }
        return true;
    }
}
