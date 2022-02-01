package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to personal page command.
 * This command transfers to users' personal page depending on user role.
 * If an exception is caught, user gets a message and is transferred to the main page.
 *
 * @author Anton Khaletski
 */

public class ToPersonalPageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Going to personal page...");
		Router router = new Router();
		HttpSession session = request.getSession();
		User.Role role = ((User) session.getAttribute(Attributes.USER)).getRole();
		if (role.equals(User.Role.ADMIN)) {
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_PERSONAL_PAGE);
			router.setPagePath(PagePaths.ADMIN);
		} else if (role.equals(User.Role.PARTICIPANT)){
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_PERSONAL_PAGE);
			router.setPagePath(PagePaths.PARTICIPANT);
		} else {
			router.setPagePath(request.getContextPath() + PagePaths.TO_MAIN_PAGE);
			router.setType(Router.Type.REDIRECT);
		}
		return router;
	}
}
