package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.UserService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * This command edits name, patronymic and surname of the current user.
 * If the user has been edited, a success message is received, else the failure message.
 * In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class EditCurrentUserCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		Map<String, String> userData = new HashMap<>();
		User user = (User) session.getAttribute(Attributes.USER);
		userData.put(Parameters.USER_NAME, request.getParameter(Parameters.USER_NAME));
		userData.put(Parameters.USER_PATRONYMIC, request.getParameter(Parameters.USER_PATRONYMIC));
		userData.put(Parameters.USER_SURNAME, request.getParameter(Parameters.USER_SURNAME));
		try {
			if (userService.edit(user, userData)) {
				session.setAttribute(Attributes.MESSAGE, "User has been edited.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "User hasn't been edited.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to edit user.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
