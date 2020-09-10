package ru.job4j.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private static final Logger logger = LoggerFactory.getLogger(SqlTracker.class);
    private final String propertyFile;
    private Connection cn;

    public SqlTracker(String propertyFile) {
        this.propertyFile = propertyFile;
        init();
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
        this.propertyFile = "app.properties";
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream(propertyFile)) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        if (cn == null) {
            logger.error("Connection to store is not initialized.");
            return null;
        }
        String addQuery = "INSERT INTO items(name, description) VALUES (?, ?) RETURNING id";
        try (PreparedStatement statement = cn.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Add item filed: no affected raws.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(String.valueOf(generatedKeys.getLong(1)));
            } else {
                throw new SQLException("Add item failed: no id obtained.");
            }
        } catch (SQLException e) {
            logger.error("Add statement error.", e);
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean rsl = true;
        String replaceQuery = "UPDATE items SET name = ?, description = ? where id = ?";
        try (PreparedStatement statement = cn.prepareStatement(replaceQuery)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setLong(3, Long.parseLong(id));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.info("Item with id = \"{}\" not found.", id);
                rsl = false;
            }
        } catch (SQLException e) {
            logger.error("Replace statement error.", e);
            rsl = false;
        }
        return rsl;
    }

    @Override
    public boolean delete(String id) {
        boolean rsl = true;
        String deleteQuery = "DELETE FROM items WHERE items.id = ?";
        try (PreparedStatement statement = cn.prepareStatement(deleteQuery)) {
            statement.setLong(1, Long.parseLong(id));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.info("Item with id = \"{}\" not found.", id);
                rsl = false;
            }
        } catch (SQLException e) {
            logger.error("Delete statement error.", e);
            rsl = false;
        }
        return rsl;
    }

    @Override
    public List<Item> findAll() {
        List<Item> rsl = null;
        String findQuery = "SELECT * FROM items";
        try (PreparedStatement statement = cn.prepareStatement(findQuery)) {
            rsl = parseResult(statement.executeQuery());
        } catch (SQLException e) {
            logger.error("Find all statement error.", e);
        }
        return rsl;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = null;
        String findQuery = "SELECT * FROM items as i WHERE i.name = ?";
        try (PreparedStatement statement = cn.prepareStatement(findQuery)) {
            statement.setString(1, key);
            rsl = parseResult(statement.executeQuery());
            if (rsl.isEmpty()) {
                logger.info("Item with name = \"{}\" not found", key);
            }
        } catch (SQLException e) {
            logger.error("Find by name statement error.", e);
        }
        return rsl;
    }

    @Override
    public Item findById(String id) {
        List<Item> rsl = null;
        String findQuery = "SELECT * FROM items as i WHERE i.id = ?";
        try (PreparedStatement statement = cn.prepareStatement(findQuery)) {
            statement.setLong(1, Long.parseLong(id));
            rsl = parseResult(statement.executeQuery());
            if (rsl.isEmpty()) {
                logger.info("Item with id = \"{}\" not found", id);
            }
        } catch (SQLException e) {
            logger.error("Find by id statement error.", e);
        }
        return rsl != null && !rsl.isEmpty() ? rsl.get(0) : null;
    }

    private List<Item> parseResult(ResultSet resultSet) throws SQLException {
        List<Item> rsl = new ArrayList<>();
        while (resultSet.next()) {
            String id = String.valueOf(resultSet.getLong("id"));
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Item item = new Item(name, description);
            item.setId(id);
            rsl.add(item);
        }
        return rsl;
    }
}

