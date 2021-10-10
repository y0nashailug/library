package business.Controllers;

import business.Book;
import business.CheckoutRecord;
import dataaccess.DataAccessFacade;

import java.util.HashMap;
import java.util.List;

public class CheckoutController {
    public boolean checkoutRecordExists(String memberId, List<String> checkoutRecordList) {
        return checkoutRecordList.contains(memberId);
    }

    public CheckoutRecord getCheckoutRecord(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> checkoutRecordHashMap = da.readCheckoutRecordMap();
		CheckoutRecord checkoutRecord = checkoutRecordHashMap.get(memberId);
		return checkoutRecord;
    }
}
