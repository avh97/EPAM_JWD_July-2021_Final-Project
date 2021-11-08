package by.khaletski.project.dao;

import by.khaletski.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAllUsers();

    List<User> findUsersBySurname(String userSurname);

    List<User> findUsersByRole(User.Role userRole);

    boolean addUser(User user, String userPassword);

    boolean removeUser(int userId);

    boolean changeUserRole(int userId, User.Role userRole);

    boolean editUserInfo(User user);

    Optional<String> findPasswordByEmail(String userEmail);

    Optional<User> findUserByEmail(String userEmail);
}
