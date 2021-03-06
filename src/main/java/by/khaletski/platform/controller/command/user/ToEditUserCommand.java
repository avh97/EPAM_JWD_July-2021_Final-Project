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
 * This command forwards to "edit user" page while saving selected user ID as session attribute.
 * If user cannot be found or an exception is caught, user receives a message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class ToEditUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"edit user\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute(Attributes.SELECTED);
        String id;
        if (((User) session.getAttribute(Attributes.USER)).getRole().equals(User.Role.ADMIN)) {
            id = request.getParameter(Parameters.ID);
        } else {
            id = String.valueOf(session.getAttribute(Attributes.ID));
        }
        try {
            Optional<User> optional = userService.find(id);
            if (optional.isPresent()) {
                session.setAttribute(Attributes.SELECTED, optional.get());
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_EDIT_USER_PAGE);
                router.setPagePath(PagePaths.EDIT_USER);
            } else {
                request.setAttribute(Attributes.MESSAGE, "Cannot go to \"edit user\" page.");
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
                router.setPagePath(PagePaths.ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE,
                    "An error occurred when trying to go to to \"edit user\" page.");
            session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
            router.setPagePath(PagePaths.ERROR);
        }
        return router;
    }
}
