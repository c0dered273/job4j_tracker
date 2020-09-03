package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingletonTest {
    @Test
    public void staticFieldSingletonTest() {
        TrackerStaticFieldSingleton obj1 = TrackerStaticFieldSingleton.getInstance();
        TrackerStaticFieldSingleton obj2 = TrackerStaticFieldSingleton.getInstance();
        assertThat(obj1, is(obj2));
    }

    @Test
    public void staticFinalFieldSingletonTest() {
        TrackerStaticFinalFieldSingleton obj1 = TrackerStaticFinalFieldSingleton.getInstance();
        TrackerStaticFinalFieldSingleton obj2 = TrackerStaticFinalFieldSingleton.getInstance();
        assertThat(obj1, is(obj2));
    }

    @Test
    public void staticClassSingletonTest() {
        TrackerStaticClassSingleton obj1 =  TrackerStaticClassSingleton.getInstance();
        TrackerStaticClassSingleton obj2 =  TrackerStaticClassSingleton.getInstance();
        assertThat(obj1, is(obj2));
    }
}
