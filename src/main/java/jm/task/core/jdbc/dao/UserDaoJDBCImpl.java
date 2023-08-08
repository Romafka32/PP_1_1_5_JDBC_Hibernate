package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private static final String CREATE = "CREATE TABLE IF NOT EXISTS mydbtest.users (\n" +
            "  id INT NOT NULL AUTO_INCREMENT,\n" +
            "  name VARCHAR(45) NOT NULL,\n" +
            "  lastName VARCHAR(45) NOT NULL,\n" +
            "  age INT(3) NOT NULL,\n" +
            "  PRIMARY KEY (id))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";
    private static final String DROP = "DROP TABLE IF EXISTS mydbtest.users";
    private static final String SAVE = "INSERT INTO users(name, lastName, age) VALUES(?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM users";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    private static final String CLEAR = "TRUNCATE table users";

    private static final Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем — " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> allUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allUsers.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
            allUsers.forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allUsers;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAR)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
