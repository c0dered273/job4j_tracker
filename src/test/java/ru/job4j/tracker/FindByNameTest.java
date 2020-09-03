package ru.job4j.tracker;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindByNameTest {
    @Test
    public void whenCheckOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream defOut = System.out;
        System.setOut(new PrintStream(out));
        Item item1 = new Item("Test");
        Item item2 = new Item("TestNot");
        Item item3 = new Item("Test");
        Store tracker = new MemTracker();
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        FindByNameAction action = new FindByNameAction();
        action.execute(new StubInput(new String[] {"Test"}), tracker);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("ID: " + item1.getId() + "    " + "Name: " + item1.getName())
                .add("ID: " + item3.getId() + "    " + "Name: " + item3.getName())
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
    }
}
