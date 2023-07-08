package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        getConnection();
        UserDao userDao = new UserDaoHibernateImpl();
        UserServiceImpl userService = new UserServiceImpl(userDao);

        userService.createUsersTable();
        System.out.println("Table used is created");

        userService.saveUser("Ilya", "Berlis", (byte) 33);
        System.out.println("User whose name is Ilya added to DB");

        userService.saveUser("Arny", "Shwartzweasley", (byte) 12);
        System.out.println("User whose name is Ilya added to DB");

        userService.saveUser("Britney", "Spears", (byte) 80);
        System.out.println("User whose name is Ilya added to DB");

        userService.saveUser("Baraka", "Bamma", (byte) 5);
        System.out.println("User whose name is Ilya added to DB");

        List<User> users = userService.getAllUsers();
        System.out.println("List all users");

        for (User user : users) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        System.out.println("Table was cleaned");

        userService.dropUsersTable();
        System.out.println("Table dropped");
    }
}
