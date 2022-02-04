package by.khaletski.platform.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Copyright custom tag
 *
 * @author Anton Khaletski
 */

public class CopyrightTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE = "<h6 style=\"text-align: center\">© A.V. Khaletski, 2021</h6>";

    @Override
    public int doStartTag() {
        try {
            pageContext.getOut().write(MESSAGE);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return SKIP_BODY;
    }
}
