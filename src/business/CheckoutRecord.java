package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = 1825622724051957822L;
    private LibraryMember libraryMember;
    private List<CheckoutRecordEntry> checkoutRecordEntries;

    public CheckoutRecord(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
        checkoutRecordEntries = new ArrayList<>();
    }

    public void addCheckoutRecordEntry(CheckoutRecordEntry checkoutRecordEntry) {
        checkoutRecordEntries.add(checkoutRecordEntry);
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
        return checkoutRecordEntries;
    }

}
