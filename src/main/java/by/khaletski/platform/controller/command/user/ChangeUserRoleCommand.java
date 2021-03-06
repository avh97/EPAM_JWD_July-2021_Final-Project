package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.service.UserService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command changes user role.
 * If the user role has been changed, a success notification is received, else a failure notification.
 * In both cases, the user is redirected to a personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class ChangeUserRoleCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		String id = request.getParameter(Parameters.ID);
		String role = request.getParameter(Parameters.ROLE);
		try {
			if (userService.changeRole(id, role)) {
				session.setAttribute(Attributes.MESSAGE, "User role has been changed.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "User role hasn't been changed.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Router.Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to change user role.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
