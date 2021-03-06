package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckoutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	HashMap<String, Book> readBooksMap();
	HashMap<String, User> readUserMap();
	HashMap<String, LibraryMember> readMemberMap();
	HashMap<String, CheckoutRecord> readCheckoutRecordMap();
	void saveMember(LibraryMember member);
	void deleteMember(String memberId);
	void saveBook(Book book);
	void saveCheckoutRecord(String memberId, CheckoutRecord checkoutRecord);
}
