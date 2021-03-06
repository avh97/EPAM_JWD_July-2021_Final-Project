package by.khaletski.platform.controller.command.topic;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.TopicDaoImpl;
import by.khaletski.platform.service.TopicService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command removes the topic by the transmitted ID.
 * If the conference has been removed, a success notification is received, else a failure notification.
 * In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class RemoveTopicCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        String id = request.getParameter(Parameters.ID);
        try {
            if (topicService.remove(id)) {
                session.setAttribute(Attributes.MESSAGE, "Topic has been removed.");
            } else {
                session.setAttribute(Attributes.MESSAGE, "Topic hasn't been removed.");
            }
            router.setPagePath(String.valueOf(session.getAttribute(Attributes.CURRENT_PAGE)));
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to remove the topic.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
