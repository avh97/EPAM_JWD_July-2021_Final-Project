package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to sign up page command
 *
 * @author Anton Khaletski
 */

public class ToSignUpCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Going to sign in page...");
		Router router = new Router();
		HttpSession session = request.getSession();
		session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_SIGN_UP_PAGE);
		router.setPagePath(PagePaths.SIGN_UP);
		return router;
	}
}
