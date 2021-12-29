package by.khaletski.project.controller.command.message;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Go to "edit message" page command.
 * This command transfers to "edit message" page while saving message ID as session attribute.
 *
 * @author Anton Khaletski
 */

public class ToEditMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"edit message\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(Attributes.SELECTED, request.getParameter(Parameters.ID));
        session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_EDIT_MESSAGE_PAGE);
        router.setPagePath(PagePaths.EDIT_MESSAGE);
        return router;
    }
}
