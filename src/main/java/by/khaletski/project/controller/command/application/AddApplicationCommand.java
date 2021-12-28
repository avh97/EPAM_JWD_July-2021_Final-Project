package by.khaletski.project.controller.command.application;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.ApplicationDaoImpl;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.ApplicationService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * This command adds new application.
 * If new application has been added, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class AddApplicationCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ApplicationService applicationService
			= new ApplicationServiceImpl(new ApplicationDaoImpl(), new ConferenceDaoImpl(), new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		Map<String, String> applicationData = new HashMap<>();
		User user = (User) session.getAttribute(Attributes.USER);
		Conference conference = (Conference) session.getAttribute(Attributes.SELECTED);
		applicationData.put(Parameters.APPLICATION_DESCRIPTION,
				request.getParameter(Parameters.APPLICATION_DESCRIPTION));
		try {
			if (applicationService.add(user, conference, applicationData)) {
				session.setAttribute(Attributes.MESSAGE, "New application has been added.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "New application hasn't been added.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to add new application.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
