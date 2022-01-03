package by.khaletski.platform.controller.command.other;

import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * This command forwards to main page when system can't define the command
 *
 * @author Anton Khaletski
 */

public class UnknownCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.info("Unknown command");
        Router router = new Router();
        router.setPagePath(PagePaths.MAIN);
        return router;
    }
}