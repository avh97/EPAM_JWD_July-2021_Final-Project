package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.UserDaoImpl;
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
 * This command adds new user.
 * If new user has been added, a success message is received and the current user is forwarded to sign-in page.
 * Else a failure message is received and the current user is forwarded to the sign-up page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class SignUpCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final UserService userService = new UserServiceImpl(new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		Map<String, String> userData = new HashMap<>();
		String name = request.getParameter(Parameters.USER_NAME);
		String patronymic = request.getParameter(Parameters.USER_PATRONYMIC);
		String surname = request.getParameter(Parameters.USER_SURNAME);
		String email = request.getParameter(Parameters.USER_EMAIL);
		String password = request.getParameter(Parameters.USER_PASSWORD);
		String confirmedPassword = request.getParameter(Parameters.USER_CONFIRMED_PASSWORD);
		userData.put(Parameters.USER_NAME, name);
		userData.put(Parameters.USER_PATRONYMIC, patronymic);
		userData.put(Parameters.USER_SURNAME, surname);
		userData.put(Parameters.USER_EMAIL, email);
		userData.put(Parameters.USER_PASSWORD, password);
		if (password.equals(confirmedPassword)) {
			try {
				if (userService.findByEmail(email).isEmpty()) {
					if (userService.add(userData)) {
						router.setPagePath(PagePaths.TO_SIGN_IN_PAGE);
						router.setType(Type.FORWARD);
						session.setAttribute(Attributes.MESSAGE, "New account has been created.");
					} else {
						router.setPagePath(PagePaths.SIGN_UP);
						request.setAttribute(Attributes.MESSAGE, "Error. New account hasn't been created.");
					}
				} else {
					router.setPagePath(PagePaths.SIGN_UP);
					request.setAttribute(Attributes.MESSAGE, "User with this email already exists.");
				}
			} catch (ServiceException e) {
				LOGGER.error(e);
				session.setAttribute(Attributes.MESSAGE,
						"An error occurred when trying to sigh up.");
				router.setPagePath(PagePaths.ERROR);
			}
		} else {
			router.setPagePath(PagePaths.SIGN_UP);
			request.setAttribute(Attributes.MESSAGE, "Passwords don't match.");
		}
		return router;
	}
}
