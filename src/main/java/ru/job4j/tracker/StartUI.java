package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class StartUI {

    public void init(Input input, MemTracker memTracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            run = actions.get(select).execute(input, memTracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        int index = 0;
        System.out.println("Menu.");
        for (ru.job4j.tracker.UserAction action : actions) {
            System.out.println(index + ". " + actions.get(index).name());
            index++;
        }
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        MemTracker memTracker = new MemTracker();
        List<UserAction> actions = new ArrayList<>();
        actions.add(new ru.job4j.tracker.CreateAction());
        actions.add(new ru.job4j.tracker.FindAllAction());
        actions.add(new ru.job4j.tracker.EditItemAction());
        actions.add(new ru.job4j.tracker.DeleteItemAction());
        actions.add(new ru.job4j.tracker.FindByIdAction());
        actions.add(new FindByNameAction());
        actions.add(new QuitAction());
        new StartUI().init(validate, memTracker, actions);
    }
}
