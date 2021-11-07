package by.khaletski.project.dao;

import by.khaletski.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAllUsers();

    List<User> findUsersBySurname(String surname);

    List<User> findUsersByRole(User.Role role);

    boolean addUser(User user, String password);

    boolean removeUser(int id);

    boolean changeUserRole(User.Role role, int id);

    boolean editUserInfo(User user);

    Optional<String> findPasswordByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
