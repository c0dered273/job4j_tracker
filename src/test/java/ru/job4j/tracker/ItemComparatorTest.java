package ru.job4j.tracker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class ItemComparatorTest {
    private List<Item> result = new ArrayList<>();

    @Before
    public void prepareDate() {
        Item item1 = new Item("Name1");
        item1.setId("111");
        Item item2 = new Item("Name2");
        item2.setId("222");
        Item item3 = new Item("Name3");
        item3.setId("333");
        Item item4 = new Item("Name2");
        item4.setId("444");
        result.add(item3);
        result.add(item2);
        result.add(item1);
        result.add(item4);
    }

    @Test
    public void whenItemsSortNatural() {
        Collections.sort(result, ItemComparator.naturalOrder());
        StringJoiner expect = new StringJoiner(", ");
        expect.add("[\"111 Name1\"");
        expect.add("\"222 Name2\"");
        expect.add("\"444 Name2\"");
        expect.add("\"333 Name3\"]");
        assertThat(result.toString(), is(expect.toString()));
    }

    @Test
    public void whenItemsSortReverse() {
        Collections.sort(result, ItemComparator.reverseOrder());
        StringJoiner expect = new StringJoiner(", ");
        expect.add("[\"333 Name3\"");
        expect.add("\"222 Name2\"");
        expect.add("\"444 Name2\"");
        expect.add("\"111 Name1\"]");
        assertThat(result.toString(), is(expect.toString()));
    }
}
