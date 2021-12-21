package by.khaletski.project.controller.command.application;

import by.khaletski.project.controller.command.Attributes;
import by.khaletski.project.controller.command.Command;
import by.khaletski.project.controller.command.PagePaths;
import by.khaletski.project.controller.command.Parameters;
import by.khaletski.project.controller.command.Router;
import by.khaletski.project.controller.command.Router.Type;
import by.khaletski.project.dao.impl.ApplicationDaoImpl;
import by.khaletski.project.dao.impl.ConferenceDaoImpl;
import by.khaletski.project.dao.impl.UserDaoImpl;
import by.khaletski.project.entity.Application.Status;
import by.khaletski.project.service.ApplicationService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.impl.ApplicationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command changes the status of the application to "rejected".
 * If the application has been changed, the user receives a success message.
 * If not, the user receives a failure message. In both cases, the user is redirected to a personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 *  @author Anton Khaletski
 */

public class ChangeApplicationToRejectedCommand implements Command {
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
            if (applicationService.changeStatus(id, Status.REJECTED)) {
                session.setAttribute(Attributes.MESSAGE, "Application status has been set to \"Rejected\".");
            } else {
                session.setAttribute(Attributes.MESSAGE, "Application status has not been set to \"Rejected\".");
            }
            router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
            router.setType(Type.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to change application status.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
