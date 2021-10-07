package business.Exceptions;

import java.io.Serializable;

public class BookException extends Exception {
    public BookException() {
        super();
    }
    public BookException(String msg) {
        super(msg);
    }
    public BookException(Throwable t) {
        super(t);
    }
}
