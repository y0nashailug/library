package business.Controllers;

import business.CheckoutRecord;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutRecordController {

    public void addCheckout(String memberId, CheckoutRecord checkoutRecord) {
        DataAccess da = new DataAccessFacade();
        da.saveCheckoutRecord(memberId, checkoutRecord);
    }
}
