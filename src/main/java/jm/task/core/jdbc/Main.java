package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Vas'a", "Pupkin", (byte) 20));
        userList.add(new User("John", "Appleseed", (byte) 50));
        userList.add(new User("Magomed", "Magodedov", (byte) 16));
        userList.add(new User("Kate", "Radio", (byte) 28));

        UserService service = new UserServiceImpl();
        service.createUsersTable();

        for (User user : userList) {
            String name = user.getName();
            String lastName = user.getLastName();
            byte age = user.getAge();
            service.saveUser(name, lastName, age);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        }

        List<User> allUsers = service.getAllUsers();

        System.out.println(allUsers);

        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
