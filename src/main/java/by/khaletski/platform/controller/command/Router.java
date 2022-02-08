package by.khaletski.platform.controller.command;

/**
 * Router class.
 *
 * @author Anton Khaletski
 */

public class Router {
    private String pagePath;
    private Type type;

    /**
     * Enum describes all possible router type.
     */

    public enum Type {
        FORWARD,
        REDIRECT
    }

    /**
     * Constructor sets default route type to "Forward".
     */

    public Router() {
        this.type = Type.FORWARD;
    }

    /**
     * Constructor sets default route type to "Forward".
     *
     * @param pagePath
     */

    public Router(String pagePath) {
        this.pagePath = pagePath;
        this.type = Type.FORWARD;
    }

    /**
     * Constructor sets selected route type.
     *
     * @param pagePath
     * @param type     (type of router)
     */

    public Router(String pagePath, Type type) {
        this.pagePath = pagePath;
        this.type = type;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
