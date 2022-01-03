package by.khaletski.platform.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface for different commands
 *
 * @author Anton Khaletski
 */

public interface Command {

	/**
	 * Executes command
	 *
	 * @param request{{@link HttpServletRequest}
	 * @return {@link Router}
	 */

	Router execute(HttpServletRequest request);
}
