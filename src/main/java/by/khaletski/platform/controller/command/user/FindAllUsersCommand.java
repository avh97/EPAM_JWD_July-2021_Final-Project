package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.service.UserService;
import by.khaletski.platform.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This command finds all users.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class FindAllUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            List<User> users = userService.findAll();
            router.setPagePath(String.valueOf(session.getAttribute(Attributes.CURRENT_PAGE)));
            request.setAttribute(Attributes.USER_LIST, users);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to find all users.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}