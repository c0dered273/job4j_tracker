package ru.job4j.tracker;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

public class FindByNameTest {
    @Test
    public void whenCheckOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream defOut = System.out;
        System.setOut(new PrintStream(out));
        Item item1 = new Item("Test", "Test desc");
        Item item2 = new Item("TestNot", "Test desc");
        Item item3 = new Item("Test", "Test desc");
        Store tracker = new MemTracker();
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        FindByNameAction action = new FindByNameAction();
        action.execute(new StubInput(new String[] {"Test"}), tracker);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("ID: " + item1.getId() + " " + "Name: " + item1.getName() + " " + "Description: " + item1.getDescription())
                .add("ID: " + item3.getId() + " " + "Name: " + item3.getName() + " " + "Description: " + item3.getDescription())
                .toString();
        assertThat(out.toString(), is(expect));
        System.setOut(defOut);
    }

    @Test
    public void whenCheckMocked() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream defOut = System.out;
        System.setOut(new PrintStream(out));
        String name = "Name";
        Item item = new Item(name, "Description");
        Store tracker = new MemTracker();
        tracker.add(item);
        FindByNameAction action = new FindByNameAction();
        Input mockInput = Mockito.mock(Input.class);
        Mockito.when(mockInput.askStr(Mockito.anyString())).thenReturn(name);
        action.execute(mockInput, tracker);
        String expect = "ID: " + item.getId() + " "
                + "Name: " + item.getName() + " "
                + "Description: " + item.getDescription() + System.lineSeparator();
        assertThat(out.toString(), is(expect));
        System.setOut(defOut);
    }
}
