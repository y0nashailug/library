package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	void login(String id, String password) throws LoginException;
	List<String> allMemberIds();
	List<String> allBookIds();
	List<String> allCheckoutRecordIds();
	HashMap<String, LibraryMember> getMembers();
	HashMap<String, CheckoutRecord> allCheckoutRecords();
}
