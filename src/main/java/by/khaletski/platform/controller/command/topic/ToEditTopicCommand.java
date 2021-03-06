package by.khaletski.platform.controller.command.topic;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.Parameters;
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
import java.util.Optional;

/**
 * This command forwards to "edit topic" page while saving topic ID as session attribute.
 * If topic cannot be found or an exception is caught, user gets a notification and is forwarded to the error page.
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
        session.removeAttribute(Attributes.SELECTED);
        String id = request.getParameter(Parameters.ID);
        try {
            Optional<Topic> optional = topicService.find(id);
            if (optional.isPresent()) {
                session.setAttribute(Attributes.SELECTED, optional.get());
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
