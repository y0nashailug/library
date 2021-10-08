package business.Exceptions;

public class LogoutException extends Exception {
    public LogoutException() {
        super();
    }
    public LogoutException(String msg) {
        super(msg);
    }
    public LogoutException(Throwable t) {
        super(t);
    }
}
