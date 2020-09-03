package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Метод реализаущий добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    /**
     * Редактирует заявку
     * @param id Уникальный идентификатор заявки
     * @param item Заявка
     * @return Результат операции
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = indexById(id);
        if (index != -1) {
            String oldId = items.get(index).getId();
            item.setId(oldId);
            items.set(index, item);
            result = true;
        }
        return result;
    }

    /**
     * Удаляет заявку
     * @param id Уникальный идентификатор заявки
     * @return Результат операции
     */
    public boolean delete(String id) {
        boolean result = false;
        int index = indexById(id);
        if (index != -1) {
            items.remove(index);
            result = true;
        }
        return result;
    }

    /**
     * Выводит все заявки
     * @return Массив заявок без null
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * Ищет заявки по имени
     * @param key Имя заявки
     * @return Массив заявок или пустой массив
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Ищет заявки по ID
     * @param id Идентификатор
     * @return Заявка или null
     */
    public Item findById(String id) {
        int index = this.indexById(id);
        if (index != -1) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        Random rm = new Random();
        return String.valueOf(rm.nextLong() + System.currentTimeMillis());
    }

    /**
     * Ищет индекс массива с заданным Id
     * @param id Идентификатор
     * @return Индекс массива или -1 если такого элемента нет
     */
    private int indexById(String id) {
        int index = 0;
        for (Item item : items) {
            if (item.getId().equals(id)) {
                break;
            }
            index++;
        }
        return index == items.size() ? -1 : index;
    }
}
