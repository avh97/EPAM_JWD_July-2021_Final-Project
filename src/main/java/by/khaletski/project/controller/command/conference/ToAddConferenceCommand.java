package by.khaletski.project.controller.command.conference;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to "add conference" page command
 *
 * @author Anton Khaletski
 */

public class ToAddConferenceCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"add conference\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_ADD_CONFERENCE_PAGE);
        router.setPagePath(PagePaths.ADD_CONFERENCE);
        return router;
    }
}