package by.khaletski.platform.controller.command.application;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.ApplicationDaoImpl;
import by.khaletski.platform.dao.impl.ConferenceDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.entity.Application;
import by.khaletski.platform.service.ApplicationService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This command finds all applications.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class FindAllApplicationsCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ApplicationService applicationService
			= new ApplicationServiceImpl(new ApplicationDaoImpl(), new ConferenceDaoImpl(), new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			List<Application> applications = applicationService.findAll();
			router.setPagePath(String.valueOf(session.getAttribute(Attributes.CURRENT_PAGE)));
			request.setAttribute(Attributes.APPLICATION_LIST, applications);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to find all applications.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
