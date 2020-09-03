package ru.job4j.tracker;

import java.util.Comparator;

public class ItemComparator {

    public static Comparator<Item> naturalOrder() {
        return NaturalOrder.NATURAL_ORDER;
    }

    public static Comparator<Item> reverseOrder() {
        return ReversedOrder.REVERSED_ORDER;
    }

    private static class NaturalOrder implements Comparator<Item> {

        private static final NaturalOrder NATURAL_ORDER = new NaturalOrder();

        @Override
        public int compare(Item o1, Item o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private static class ReversedOrder implements Comparator<Item> {

        private static final ReversedOrder REVERSED_ORDER = new ReversedOrder();

        @Override
        public int compare(Item o1, Item o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }
}
