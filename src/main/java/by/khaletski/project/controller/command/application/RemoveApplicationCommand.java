package by.khaletski.project.controller.command.application;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.dao.impl.ApplicationDaoImpl;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.service.ApplicationService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command removes the application by the transmitted ID.
 * If the application has been removed, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure message and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class RemoveApplicationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ApplicationService applicationService
            = new ApplicationServiceImpl(new ApplicationDaoImpl(), new ConferenceDaoImpl(), new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter(Parameters.ID));
        try {
            if (applicationService.remove(id)) {
                session.setAttribute(Attributes.MESSAGE, "Application has been removed.");
            } else {
                session.setAttribute(Attributes.MESSAGE, "Application hasn't been removed.");
            }
            router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to remove the application.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
