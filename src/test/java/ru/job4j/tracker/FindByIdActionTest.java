package ru.job4j.tracker;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

public class FindByIdActionTest {
    @Test
    public void whenFindByIdMocked() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream defOut = System.out;
        System.setOut(new PrintStream(out));
        Item item = new Item("name", "Description");
        Store tracker = new MemTracker();
        tracker.add(item);
        FindByIdAction action = new FindByIdAction();
        Input mockInput = Mockito.mock(Input.class);
        Mockito.when(mockInput.askStr(Mockito.anyString())).thenReturn(item.getId());
        action.execute(mockInput, tracker);
        String expect = "ID: " + item.getId() + " "
                + "Name: " + item.getName() + " "
                + "Description: " + item.getDescription() + System.lineSeparator();
        assertThat(out.toString(), is(expect));
        System.setOut(defOut);
    }
}