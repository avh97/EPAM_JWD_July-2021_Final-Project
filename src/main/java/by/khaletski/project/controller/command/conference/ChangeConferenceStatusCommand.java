package by.khaletski.project.controller.command.conference;

import by.khaletski.project.controller.command.*;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.TopicDaoImpl;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.ConferenceService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ConferenceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command changes conference.
 * If the conference has been changed, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user is redirected to a personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class ChangeConferenceStatusCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ConferenceService conferenceService
			= new ConferenceServiceImpl(new ConferenceDaoImpl(), new TopicDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter(Parameters.ID));
		Conference.Status status = Conference.Status.valueOf(request.getParameter(Parameters.STATUS));
		try {
			if (conferenceService.changeStatus(id, status)) {
				session.setAttribute(Attributes.MESSAGE, "Conference status has been changed.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "Conference status hasn't been changed.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to change conference status.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}