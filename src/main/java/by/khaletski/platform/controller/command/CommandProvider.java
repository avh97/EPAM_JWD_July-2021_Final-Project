package by.khaletski.platform.controller.command;

import by.khaletski.platform.controller.command.other.UnknownCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CommandProvider class.
 *
 * @author Anton Khaletski
 */

public class CommandProvider {
	private static final Logger LOGGER = LogManager.getLogger();

	public static Command defineCommand(String command) {
		Command current;
		LOGGER.debug(command);
		if (command == null || command.isEmpty()) {
			LOGGER.debug("Empty command");
			return new UnknownCommand();
		}
		try {
			CommandEnum currentType = CommandEnum.valueOf(command.toUpperCase());
			current = currentType.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			LOGGER.debug("Empty command from catch block");
			current = new UnknownCommand();
		}
		return current;
	}
}
