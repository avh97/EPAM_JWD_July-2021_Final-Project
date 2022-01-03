package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to sign in page command
 *
 * @author Anton Khaletski
 */

public class ToSignInCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Going to sign in page...");
		Router router = new Router();
		HttpSession session = request.getSession();
		session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_SIGN_IN_PAGE);
		router.setPagePath(PagePaths.SIGN_IN);
		return router;
	}
}
