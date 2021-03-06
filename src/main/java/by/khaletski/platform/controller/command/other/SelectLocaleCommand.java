package by.khaletski.platform.controller.command.other;

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
 * This command selects locale.
 *
 * @author Anton Khaletski
 */

public class SelectLocaleCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        if (session.getAttribute(Attributes.USER) == null) {
            router.setPagePath(PagePaths.TO_MAIN_PAGE);
        } else {
            router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
        }
        String language = request.getParameter(Parameters.LANGUAGE);
        session.setAttribute(Attributes.LOCALE, language);
        LOGGER.debug(language);
        return router;
    }
}
