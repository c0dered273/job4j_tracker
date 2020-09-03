package ru.job4j.tracker;

public class StubAction implements UserAction {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub Action";
    }

    @Override
    public boolean execute(Input input, MemTracker memTracker) {
        this.call = true;
        return false;
    }

    public boolean isCall() {
        return this.call;
    }
}
