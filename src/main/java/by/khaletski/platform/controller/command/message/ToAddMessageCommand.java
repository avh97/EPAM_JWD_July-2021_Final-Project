package by.khaletski.platform.controller.command.message;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command forwards to "add message" page.
 *
 * @author Anton Khaletski
 */

public class ToAddMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Going to \"add message\" page...");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(Attributes.CURRENT_PAGE, PagePaths.TO_ADD_MESSAGE_PAGE);
        router.setPagePath(PagePaths.ADD_MESSAGE);
        return router;
    }
}
