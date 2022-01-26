package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.controller.command.Router.Type;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.service.UserService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		String name = request.getParameter(Parameters.NAME);
		String patronymic = request.getParameter(Parameters.PATRONYMIC);
		String surname = request.getParameter(Parameters.SURNAME);
		String email = request.getParameter(Parameters.EMAIL);
		String password = request.getParameter(Parameters.PASSWORD);
		String confirmedPassword = request.getParameter(Parameters.CONFIRMED_PASSWORD);
		if (password.equals(confirmedPassword)) {
			try {
				if (userService.findByEmail(email).isEmpty()) {
					if (userService.add(password, email, name, patronymic, surname)) {
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
