package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

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

    private static final Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(CREATE);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(DROP);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(SAVE);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем — " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allUsers.add(new User(resultSet.getString("name"),
                                        resultSet.getString("lastName"),
                                        resultSet.getByte("age")));
            }
            allUsers.stream().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
        return allUsers;
    }

    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement(CLEAR);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (util.getConnection() != null) {
                util.getConnection().close();
            }
        }
    }
}
