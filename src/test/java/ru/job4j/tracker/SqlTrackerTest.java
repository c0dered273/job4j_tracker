package ru.job4j.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class SqlTrackerTest {
    private final Item item1 = new Item("Item1", "Desc01");
    private final Item item2 = new Item("Item2", "Desc02");
    private final Item item3 = new Item("Item3", "Desc02");

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenAddItem() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            assertThat(tracker.findByName(item1.getName()).size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteItem() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.delete(item1.getId());
            assertThat(tracker.findById(item1.getId()), nullValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplaceItem() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(item1);
            tracker.add(item2);
            tracker.replace(item1.getId(), item3);
            assertThat(tracker.findByName(item3.getName()).size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}