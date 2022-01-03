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
 * This command removes the message by the transmitted ID.
 * If the message has been removed, the user receives a success notification.
 * If not, the user receives a failure notification. In both cases, the user remains on the current page.
 * If an exception is caught, the user receives a failure notification and is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class RemoveMessageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final MessageService messageService
			= new MessageServiceImpl(new MessageDaoImpl(), new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		try {
			if (messageService.remove(Integer.parseInt(request.getParameter(Parameters.ID)))) {
				session.setAttribute(Attributes.MESSAGE, "Message has been removed.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "Message hasn't been removed.");
			}
			router.setPagePath((String) session.getAttribute(Attributes.CURRENT_PAGE));
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to remove the message.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
