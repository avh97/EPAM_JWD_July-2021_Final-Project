package by.khaletski.platform.controller.command.message;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.impl.MessageDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.entity.Message;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.MessageService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This command finds all messages of selected user.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class FindUserMessagesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final MessageService messageService
            = new MessageServiceImpl(new MessageDaoImpl(), new UserDaoImpl());

    @Override
    public Router execute(HttpServletRequest request) {
        LOGGER.debug("Attempt to execute command");
        Router router = new Router();
        HttpSession session = request.getSession();
        int id = ((User) session.getAttribute(Attributes.USER)).getId();
        try {
            List<Message> messages = messageService.findByUserId(id);
            router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
            request.setAttribute(Attributes.MESSAGE_LIST, messages);
        } catch (ServiceException e) {
            LOGGER.error(e);
            session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to find messages.");
            router.setPagePath(PagePaths.ERROR);
        }
        LOGGER.debug("Command has been executed");
        return router;
    }
}
