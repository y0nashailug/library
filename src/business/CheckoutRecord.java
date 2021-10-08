package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = 1825622724051957822L;
    private String memberId;
    private BookCopy bookCopy;
    private LocalDate checkoutDate;
    private LocalDate dueDate;

    public CheckoutRecord(String memberId, BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate) {
        this.memberId = memberId;
        this.bookCopy = bookCopy;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public String getMemberId() {
        return memberId;
    }
    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
