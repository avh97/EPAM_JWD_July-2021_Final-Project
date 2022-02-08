package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
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

/**
 * This command edits name, patronymic and surname of the selected user.
 * If the user has been edited (in case selected user is current user, session attribute "user" is updated too)
 * a success notification is received, else the failure notification.
 * In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class EditUserCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attributes.SELECTED);
		String email = request.getParameter(Parameters.EMAIL);
		String name = request.getParameter(Parameters.NAME);
		String patronymic = request.getParameter(Parameters.PATRONYMIC);
		String surname = request.getParameter(Parameters.SURNAME);
		try {
			if (userService.edit(user, email, name, patronymic, surname)) {
				session.setAttribute(Attributes.MESSAGE, "User has been edited.");
				if (user.getId() == ((User) session.getAttribute(Attributes.USER)).getId()) {
					session.setAttribute(Attributes.USER, user);
				}
			} else {
				session.setAttribute(Attributes.MESSAGE, "User hasn't been edited.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Router.Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE,
					"An error occurred when trying to edit user.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
