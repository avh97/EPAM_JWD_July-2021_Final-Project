package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.service.UserService;
import by.khaletski.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command removes the user by the transmitted ID.
 * If the user has been removed, a success message is received, else a failure message.
 * In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class RemoveUserCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			if (userService.remove(Integer.parseInt(request.getParameter(Parameters.ID)))) {
				session.setAttribute(Attributes.MESSAGE,
						"User has been removed.");
			} else {
				session.setAttribute(Attributes.MESSAGE,
						"User hasn't been removed.");
			}
			router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE,
					"An error occurred when trying to remove the user.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
