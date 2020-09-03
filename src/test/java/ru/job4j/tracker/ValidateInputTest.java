package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateInputTest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream stdOut = System.out;

    @Before
    public void setOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void resetOutput() {
        System.setOut(this.stdOut);
    }

    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"invalid", "1"})
        );
        input.askInt("Enter", 1);
        assertThat(
                this.out.toString(),
                is(String.format("Please enter validate data again %n"))
        );
    }

    @Test
    public void whenOutOfBoundInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"99", "1"})
        );
        input.askInt("Enter", 1);
        assertThat(
                this.out.toString(),
                is(String.format("Please select key from menu %n"))
        );
    }
}
