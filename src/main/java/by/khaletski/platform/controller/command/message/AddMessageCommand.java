package by.khaletski.platform.controller.command.message;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.controller.command.Router.Type;
import by.khaletski.platform.dao.impl.MessageDaoImpl;
import by.khaletski.platform.dao.impl.UserDaoImpl;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.MessageService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * This command adds new message.
 * If new message has been added, the user receives a success notification.
 * If not, the user receives a failure notification. In both cases, the user is redirected to the personal page.
 * If an exception is caught, the user is forwarded to the error page.
 *
 * @author Anton Khaletski
 */

public class AddMessageCommand implements Command {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final MessageService messageService
			= new MessageServiceImpl(new MessageDaoImpl(), new UserDaoImpl());

	@Override
	public Router execute(HttpServletRequest request) {
		LOGGER.debug("Attempt to execute command");
		Router router = new Router();
		HttpSession session = request.getSession();
		Map<String, String> messageData = new HashMap<>();
		String id = String.valueOf(((User) session.getAttribute(Attributes.USER)).getId());
		messageData.put(Parameters.USER_ID, id);
		messageData.put(Parameters.QUESTION, request.getParameter(Parameters.QUESTION));
		try {
			if (messageService.add(messageData)) {
				session.setAttribute(Attributes.MESSAGE, "New message has been added.");
			} else {
				session.setAttribute(Attributes.MESSAGE, "New message hasn't been added.");
			}
			router.setPagePath(request.getContextPath() + PagePaths.TO_PERSONAL_PAGE);
			router.setType(Type.REDIRECT);
		} catch (ServiceException e) {
			LOGGER.error(e);
			session.setAttribute(Attributes.MESSAGE, "An error occurred when trying to add new message.");
			router.setPagePath(PagePaths.ERROR);
		}
		LOGGER.debug("Command has been executed");
		return router;
	}
}
