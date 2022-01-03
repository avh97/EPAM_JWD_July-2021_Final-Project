package by.khaletski.platform.controller.command.conference;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.ConferenceDaoImpl;
import by.khaletski.platform.dao.impl.TopicDaoImpl;
import by.khaletski.platform.service.ConferenceService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.ConferenceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command removes the conference by the transmitted ID.
 * If the conference has been removed, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class RemoveConferenceCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ConferenceService conferenceService
			= new ConferenceServiceImpl(new ConferenceDaoImpl(), new TopicDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			if (conferenceService.remove(Integer.parseInt(request.getParameter(Parameters.ID)))) {
				session.setAttribute(Attributes.MESSAGE, "Conference has been removed.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "Conference hasn't been removed.");
			}
			router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to remove the conference.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
