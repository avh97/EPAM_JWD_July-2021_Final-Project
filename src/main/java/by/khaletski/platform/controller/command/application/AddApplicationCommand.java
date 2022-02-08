package by.khaletski.platform.controller.command.application;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.ApplicationDaoImpl;
import by.khaletski.platform.dao.impl.ConferenceDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.service.ApplicationService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command adds new application.
 * If new application has been added, a success notification is received, else a failure notification.
 * In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class AddApplicationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ApplicationService applicationService
            = new ApplicationServiceImpl(new ApplicationDaoImpl(), new ConferenceDaoImpl(), new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        String userId = String.valueOf(session.getAttribute(Attributes.ID));
        String conferenceId = String.valueOf(session.getAttribute(Attributes.SELECTED));
        String description = request.getParameter(Parameters.DESCRIPTION);
        try {
            if (applicationService.add(userId, conferenceId, description)) {
                session.setAttribute(Attributes.MESSAGE, "New application has been added.");
            } else {
                session.setAttribute(Attributes.MESSAGE, "New application hasn't been added.");
            }
            router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
            router.setType(Router.Type.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to add new application.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
