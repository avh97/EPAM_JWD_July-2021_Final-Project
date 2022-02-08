package by.khaletski.platform.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

	Router execute(HttpServletRequest request);
}
