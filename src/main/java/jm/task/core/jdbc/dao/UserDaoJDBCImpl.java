package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String create = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(40) NOT NULL, " +
                    "lastName VARCHAR (40) NOT NULL, " +
                    "age TINYINT NOT NULL" +
                    ")";

            statement.executeUpdate(create);

            System.out.println("Users table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            String deleteTable = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(deleteTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String save = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(save)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            statement.close();
            System.out.println("User saved successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(remove)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User was removed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getUsers = "SELECT * FROM USERS";

        try (PreparedStatement statement = connection.prepareStatement(getUsers);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        String clean = "DELETE FROM users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(clean);

            System.out.println("Users table cleaned successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
