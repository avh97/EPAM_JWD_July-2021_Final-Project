package by.khaletski.project.service.impl;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.util.PasswordEncoder;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAllUsers() {
        List<User> userList;
        userList = userDao.findAllUsers();
        return userList;
    }

    public List<User> findUsersBySurname(String userSurname) {
        List<User> userList = new ArrayList<>();
        if (Validator.isValidName(userSurname)) {
            userList = userDao.findUsersBySurname(userSurname);
        }
        return userList;
    }

    public List<User> findUsersByRole(User.Role userRole) {
        List<User> userList;
        userList = userDao.findUsersByRole(userRole);
        return userList;
    }

    public boolean addUser(User user, String userPassword) {
        boolean isAdded;
        isAdded = userDao.addUser(user, userPassword);
        return isAdded;
    }

    public boolean removeUser(int id) {
        boolean isRemoved;
        isRemoved = userDao.removeUser(id);
        return isRemoved;
    }

    public boolean changeUserRole(User.Role role, int id) {
        boolean isChanged;
        isChanged = userDao.changeUserRole(id, role);
        return isChanged;
    }

    public boolean editUserInfo(User user) {
        boolean isEdited;
        isEdited = userDao.editUserInfo(user);
        return isEdited;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = Optional.empty();
        if (Validator.isValidEmail(email)) {
            String encodedPassword = PasswordEncoder.encodePassword(password);
            Optional<String> passwordFromDBOptional = userDao.findPasswordByEmail(email);
            if (passwordFromDBOptional.isPresent()) {
                String passwordFromDB = passwordFromDBOptional.get();
                if (passwordFromDB.equals(encodedPassword)) {
                    optionalUser = userDao.findUserByEmail(email);
                } else {
                    optionalUser = Optional.empty();
                }
            } else {
                optionalUser = Optional.empty();
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
