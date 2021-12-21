package by.khaletski.project.controller.command.conference;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.TopicDaoImpl;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.service.ConferenceService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ConferenceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Go to "edit conference" page command.
 * This command transfers to "edit conference" page while saving conference ID as an attribute from current page.
 * If conference cannot be found or an exception is caught, user gets a message and is transferred to the error page.
 *
 * @author Anton Khaletski
 */

public class ToEditConferenceCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConferenceService conferenceService
            = new ConferenceServiceImpl(new ConferenceDaoImpl(), new TopicDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"edit conference\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter(Parameters.ID));
        try {
            Optional<Conference> optional = conferenceService.find(id);
            if (optional.isPresent()) {
                request.setAttribute(Attributes.CONFERENCE, optional.get());
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_EDIT_CONFERENCE_PAGE);
                router.setPagePath(PagePaths.EDIT_CONFERENCE);
            } else {
                request.setAttribute(Attributes.MESSAGE, "Cannot go to \"edit conference\" page.");
                session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
                router.setPagePath(PagePaths.ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE,
                    "An error occurred when trying to go to \"edit conference\" page.");
            session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.ERROR);
            router.setPagePath(PagePaths.ERROR);
        }
        return router;
    }
}