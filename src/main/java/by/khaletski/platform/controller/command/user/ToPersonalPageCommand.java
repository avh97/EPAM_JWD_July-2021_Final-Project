package by.khaletski.platform.controller.command.user;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.ApplicationDaoImpl;
import by.khaletski.platform.dao.impl.ConferenceDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.ApplicationService;
import by.khaletski.platform.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to personal page command.
 * This command transfers to users' personal page depending on user role.
 * If role cannot be found or an exception is caught, user gets a message and is transferred to the sign-in page.
 *
 * @author Anton Khaletski
 */

public class ToPersonalPageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ApplicationService applicationService
			= new ApplicationServiceImpl(new ApplicationDaoImpl(), new ConferenceDaoImpl(), new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Going to personal page...");
		Router router = new Router();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Attributes.USER);
		if (user != null && user.getRole().equals(User.Role.ADMIN)) {
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_PERSONAL_PAGE);
			router.setPagePath(PagePaths.ADMIN);
		} else if (user != null && user.getRole().equals(User.Role.OBSERVER)){
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_PERSONAL_PAGE);
			router.setPagePath(PagePaths.OBSERVER);
		} else if (user != null && user.getRole().equals(User.Role.PARTICIPANT)){
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_PERSONAL_PAGE);
			router.setPagePath(PagePaths.PARTICIPANT);
		} else {
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_SIGN_IN_PAGE);
			router.setPagePath(PagePaths.SIGN_IN);
		}
		return router;
	}
}
