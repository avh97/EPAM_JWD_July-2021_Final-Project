package by.khaletski.project.controller.command.user;

import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command logs out the user. Current session gets invalidated and the user is forwarded to the main page.
 *
 * @author Anton Khaletski
 */

public class LogOutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        router.setPagePath(PagePaths.TO_MAIN_PAGE);
        LOGGER.debug("Command has been executed");
        return router;
    }
}
