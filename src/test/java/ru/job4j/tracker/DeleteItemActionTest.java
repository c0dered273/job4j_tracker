package ru.job4j.tracker;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

public class DeleteItemActionTest {
    @Test
    public void whenDeleteByIdMocked() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream defOut = System.out;
        System.setOut(new PrintStream(out));
        Item item = new Item("name", "Description");
        Store tracker = new MemTracker();
        tracker.add(item);
        DeleteItemAction action = new DeleteItemAction();
        Input mockInput = Mockito.mock(Input.class);
        Mockito.when(mockInput.askStr(Mockito.anyString())).thenReturn(item.getId());
        action.execute(mockInput, tracker);
        String expectOk = "OK" + System.lineSeparator();
        assertThat(out.toString(), is(expectOk));
        action.execute(mockInput, tracker);
        String expectNotOk = "OK" + System.lineSeparator() + "Item not found" + System.lineSeparator();
        assertThat(out.toString(), is(expectNotOk));
        System.setOut(defOut);
    }
}