package by.khaletski.project.exception;

/**
 * Dao exception class
 *
 * @author Anton Khaletski
 */

public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(final String message) {
        super(message);
    }

    public DaoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DaoException(final Throwable cause) {
        super(cause);
    }
}
