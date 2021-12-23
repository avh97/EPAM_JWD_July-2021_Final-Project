package by.khaletski.project.controller.command.other;

import by.khaletski.project.controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Select locale command
 *
 * @author Anton Khaletski
 */

public class SelectLocaleCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        router.setPagePath(PagePaths.TO_MAIN_PAGE);
        String language = request.getParameter(Parameters.LANGUAGE);
        session.setAttribute(Attributes.LOCALE, language);
        LOGGER.debug(language);
        return router;
    }
}
