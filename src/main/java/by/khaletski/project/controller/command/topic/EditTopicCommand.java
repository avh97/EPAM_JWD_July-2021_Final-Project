package by.khaletski.project.controller.command.topic;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.TopicDaoImpl;
import by.khaletski.project.service.TopicService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.TopicServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * This command edits name and description in the selected topic.
 * If the topic has been edited, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class EditTopicCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        Map<String, String> topicData = new HashMap<>();
        topicData.put(Parameters.ID, request.getParameter(Parameters.ID));
        topicData.put(Parameters.TOPIC_NAME, request.getParameter(Parameters.TOPIC_NAME));
        topicData.put(Parameters.TOPIC_DESCRIPTION, request.getParameter(Parameters.TOPIC_DESCRIPTION));
        try {
            if (topicService.edit(topicData)) {
                session.setAttribute(Attributes.MESSAGE,"Topic has been edited.");
            } else {
                session.setAttribute(Attributes.MESSAGE, "Topic hasn't been edited.");
            }
            router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
            router.setType(Type.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to edit topic.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}