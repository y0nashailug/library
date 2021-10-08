package business.Exceptions;

public class CheckoutRecordException extends Exception {
    public CheckoutRecordException() {
        super();
    }
    public CheckoutRecordException(String msg) {
        super(msg);
    }
    public CheckoutRecordException(Throwable t) {
        super(t);
    }
}
