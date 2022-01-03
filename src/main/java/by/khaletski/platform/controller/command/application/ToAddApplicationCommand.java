package by.khaletski.platform.controller.command.application;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to "add application" page command.
 * This command transfers to "add application" page while saving conference as session attribute.
 * If conference cannot be found or an exception is caught, user gets a message and is transferred to the error page.
 *
 * @author Anton Khaletski
 */

public class ToAddApplicationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"add application\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute(Attributes.SELECTED);
        session.setAttribute(Attributes.SELECTED, request.getParameter(Parameters.ID));
        session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_ADD_APPLICATION_PAGE);
        router.setPagePath(PagePaths.ADD_APPLICATION);
        return router;
    }
}