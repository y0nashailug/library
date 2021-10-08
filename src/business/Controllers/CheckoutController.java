package business.Controllers;

import business.Book;
import business.CheckoutRecord;
import dataaccess.DataAccessFacade;

import java.util.List;

public class CheckoutController {
    public boolean checkoutRecordExists(String memberId, List<String> checkoutRecordList) {
        return checkoutRecordList.contains(memberId);
    }

    public CheckoutRecord getCheckoutRecord(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
        return da.getCheckoutRecord(memberId);
    }

}
