package by.khaletski.platform.controller.command.topic;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.TopicDaoImpl;
import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.service.TopicService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This command finds all topics.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class FindAllTopicsCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			List<Topic> topics = topicService.findAll();
			router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
			request.setAttribute(Attributes.TOPIC_LIST, topics);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to find all topics.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
