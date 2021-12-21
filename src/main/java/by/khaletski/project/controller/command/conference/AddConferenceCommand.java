package by.khaletski.project.controller.command.conference;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.TopicDaoImpl;
import by.khaletski.project.service.ConferenceService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ConferenceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * This command adds new conference.
 * If new conference has been added, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class AddConferenceCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ConferenceService conferenceService
			= new ConferenceServiceImpl(new ConferenceDaoImpl(), new TopicDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		Map<String, String> conferenceData = new HashMap<>();
		conferenceData.put(Parameters.TOPIC_ID, request.getParameter(Parameters.TOPIC_ID));
		conferenceData.put(Parameters.CONFERENCE_NAME, request.getParameter(Parameters.CONFERENCE_NAME));
		conferenceData.put(Parameters.CONFERENCE_DESCRIPTION, request.getParameter(Parameters.CONFERENCE_DESCRIPTION));
		conferenceData.put(Parameters.CONFERENCE_DATE, request.getParameter(Parameters.CONFERENCE_DATE));
		try {
			if (conferenceService.add(conferenceData)) {
				session.setAttribute(Attributes.MESSAGE, "New conference has been added.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "New conference hasn't been added.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to add new conference.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
