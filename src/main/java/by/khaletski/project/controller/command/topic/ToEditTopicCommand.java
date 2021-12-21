package by.khaletski.project.controller.command.topic;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.Parameters;
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
import java.util.Optional;

/**
 * Go to "edit topic" page command.
 * This command transfers to "edit topic" page while saving topic ID as an attribute from current page.
 * If topic cannot be found or an exception is caught, user gets a message and is transferred to the error page.
 *
 * @author Anton Khaletski
 */

public class ToEditTopicCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"edit topic\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter(Parameters.ID));
        try {
            Optional<Topic> optional = topicService.find(id);
            if (optional.isPresent()) {
                request.setAttribute(Attributes.TOPIC, optional.get());
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_EDIT_TOPIC_PAGE);
                router.setPagePath(PagePaths.EDIT_TOPIC);
            } else {
                request.setAttribute(Attributes.MESSAGE, "Cannot go to \"edit topic\" page.");
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
                router.setPagePath(PagePaths.ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE,
                    "An error occurred when trying to go to to \"edit topic\" page.");
            session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
            router.setPagePath(PagePaths.ERROR);
        }
        return router;
    }
}
