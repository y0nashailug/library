package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckoutRecordEntry implements Serializable {

    public static final long serialVersionUID = -6680303569454719218L;
    private BookCopy bookCopy;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate finePaidDate;
    private List<String> penalities;

    public CheckoutRecordEntry(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
        this.checkoutDate = LocalDate.now();
        this.dueDate = LocalDate.now().minusDays(bookCopy.getBook().getMaxCheckoutLength());
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

    public LocalDate getFinePaidDate() {
        return finePaidDate;
    }

    public List<String> getPenalities() {
        return penalities;
    }

    public String toString() {
        return "Checkout date: " + checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                "Due date: " + dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                bookCopy.toString();
    }
}
