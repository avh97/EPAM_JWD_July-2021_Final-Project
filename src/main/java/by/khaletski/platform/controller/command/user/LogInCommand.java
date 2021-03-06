package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.UserService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Log in command
 * If the username and password are correct, the user is transferred to the personal page,
 * session attributes "user" and "id" are also set.
 * If not, it stays on current page. In both cases, the user receives a corresponding notification.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
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
        String email = request.getParameter(Parameters.EMAIL);
        String password = request.getParameter(Parameters.PASSWORD);
        try {
            Optional<User> optional = userService.findByEmailAndPassword(email, password);
            if (optional.isPresent()) {
                User user = optional.get();
                router.setPagePath(PagePaths.TO_PERSONAL_PAGE);
                session.setAttribute(Attributes.USER, user);
                session.setAttribute(Attributes.ID, user.getId());
                session.setAttribute(Attributes.MESSAGE, "Successful log in.");
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
