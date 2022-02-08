package by.khaletski.platform.controller.command.message;

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
 * This command forwards to "edit message" page while saving message ID as session attribute.
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
        session.removeAttribute(Attributes.SELECTED);
        session.setAttribute(Attributes.SELECTED, request.getParameter(Parameters.ID));
        session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_EDIT_MESSAGE_PAGE);
        router.setPagePath(PagePaths.EDIT_MESSAGE);
        return router;
    }
}
