package by.khaletski.platform.controller;

import by.khaletski.platform.controller.command.Command;
import by.khaletski.platform.controller.command.CommandProvider;
import by.khaletski.platform.controller.command.PagePaths;
import by.khaletski.platform.controller.command.Parameters;
import by.khaletski.platform.controller.command.Router;
import by.khaletski.platform.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for queries from client.
 *
 * @author Anton Khaletski
 */

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() {
        LOGGER.info("Controller initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandFromPage = request.getParameter(Parameters.COMMAND);
        Command command = CommandProvider.defineCommand(commandFromPage);
        Router router = command.execute(request);
        switch (router.getType()) {
            case FORWARD:
                LOGGER.debug("Forward");
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(router.getPagePath());
                LOGGER.debug("Redirect");
                break;
            default:
                LOGGER.error(router.getType());
                response.sendRedirect(PagePaths.MAIN);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
