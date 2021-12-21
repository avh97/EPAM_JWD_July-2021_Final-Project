package by.khaletski.project.controller.command.other;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.dao.impl.TopicDaoImpl;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.service.TopicService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Go to main page command.
 * This command transfers to main page while saving topic ID as an attribute from current page.
 * If topic cannot be found or an exception is caught, user gets a message and is transferred to the error page.
 *
 * @author Anton Khaletski
 */

public class ToMainCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Going to main page...");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			List<Topic> topics = topicService.findAll();
			session.setAttribute(Attributes.MAIN_LIST, topics);
			router.setPagePath(PagePaths.MAIN);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to go to to main page.");
			session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
			router.setPagePath(PagePaths.ERROR);
		}

		return router;
	}
}
