package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Laura", "Palmer", (byte) 17);
        userService.saveUser("Dail", "Cooper", (byte) 31);
        userService.saveUser("Benjamine", "Horn", (byte) 45);
        userService.saveUser("Harry", "Truman", (byte) 35);
        userService.removeUserById(4);

        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
