package business;

import java.time.LocalDate;

public class CheckoutRecord {

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
