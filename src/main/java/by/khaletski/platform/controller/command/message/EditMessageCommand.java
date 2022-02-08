package by.khaletski.platform.controller.command.message;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.MessageDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.service.MessageService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This command edits answer in the selected message.
 * If the message has been edited, a success notification is received, else a failure notification.
 * In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class EditMessageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final MessageService messageService
            = new MessageServiceImpl(new MessageDaoImpl(), new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        String id = String.valueOf(session.getAttribute(Attributes.SELECTED));
        String answer = request.getParameter(Parameters.ANSWER);
        try {
            if (messageService.edit(id, answer)) {
                session.setAttribute(Attributes.MESSAGE, "Message has been edited.");
            } else {
                session.setAttribute(Attributes.MESSAGE, "Message hasn't been edited.");
            }
            router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
            router.setType(Router.Type.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to edit message.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
