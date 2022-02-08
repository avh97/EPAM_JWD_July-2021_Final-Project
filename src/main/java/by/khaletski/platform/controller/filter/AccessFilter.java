package by.khaletski.platform.controller.filter;

import by.khaletski.platform.controller.command.Attributes;
import by.khaletski.platform.controller.command.PagePaths;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Access filter defines pages that are accessible without log in.
 *
 * @author Anton Khaletski
 */

@WebFilter(filterName = "AccessFilter", dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD}, urlPatterns = "*.jsp")
public class AccessFilter implements Filter {
    private static final Set<String> ALLOWED_PATH_GUEST = new HashSet<>(Arrays.asList("/index.jsp", "/jsp/main.jsp",
            "/jsp/about.jsp", "/jsp/error.jsp", "/jsp/sign_in.jsp", "/jsp/sign_up.jsp"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String pagePath = req.getServletPath();
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute(Attributes.USER) != null);
        boolean allowedPath = ALLOWED_PATH_GUEST.contains(pagePath);
        if (loggedIn || allowedPath) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + PagePaths.TO_MAIN_PAGE);
        }
    }
}
