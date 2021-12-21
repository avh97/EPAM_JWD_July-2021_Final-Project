package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.entity.User;
import by.khaletski.project.entity.User.Role;
import by.khaletski.project.service.UserService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Log in command
 *
 * @author Anton Khaletski
 */

public class LogInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        String email = request.getParameter(Parameters.USER_EMAIL);
        String password = request.getParameter(Parameters.USER_PASSWORD);
        try {
            Optional<User> optional = userService.findByEmailAndPassword(email, password);
            if (optional.isPresent()) {
                User user = optional.get();
                Role role = user.getRole();
                if (role.equals(Role.ADMIN) || role.equals(Role.PARTICIPANT) || role.equals(Role.OBSERVER)) {
                    router.setPagePath(PagePaths.TO_PERSONAL_PAGE);
                }
                session.setAttribute(Attributes.USER, user);
                session.setAttribute(Attributes.MESSAGE, "Successful log in.");
                session.setAttribute(Attributes.NAME, user.getName());
                session.setAttribute(Attributes.PATRONYMIC, user.getPatronymic());
            } else {
                router.setPagePath(PagePaths.SIGN_IN);
                request.setAttribute(Attributes.MESSAGE, "Incorrect email or password.");
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE,
                    "An error occurred when trying to log in.");
            router.setPagePath(PagePaths.ERROR);
        }
        return router;
    }
}