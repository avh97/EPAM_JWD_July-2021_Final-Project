package by.khaletski.platform.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * This filter sets encoding, corrects the encoding of the request and response.
 *
 * @author Anton Khaletski
 */

@WebFilter(filterName = "Encoding", urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private String code;

    @Override
    public void init(FilterConfig fConfig) {
        code = fConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (codeRequest == null || !codeRequest.equalsIgnoreCase(code)) {
            request.setCharacterEncoding(code);
        }
        String codeResponse = response.getCharacterEncoding();
        if (codeResponse == null || !codeResponse.equalsIgnoreCase(code)) {
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }
}
