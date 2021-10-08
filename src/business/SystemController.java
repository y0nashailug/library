package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import business.Controllers.BookController;
import business.Controllers.CheckoutController;
import business.Controllers.MemberController;
import business.Exceptions.BookException;
import business.Exceptions.CheckoutRecordException;
import business.Exceptions.LibrarySystemException;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;
import librarysystem.UIController;
import librarysystem.Util;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		System.out.println(currentAuth.name());

		if(currentAuth.name().equals(Auth.LIBRARIAN.toString())) {
			LibrarySystem.hideAllWindows();
			String[] menu = Util.ALL_MENU;
			UIController.INSTANCE.setItems(new ArrayList<>(Arrays.asList(menu)));
			UIController.INSTANCE.init();
			UIController.INSTANCE.setVisible(true);
		} else if(currentAuth.name().equals(Auth.ADMIN.toString())) {
			LibrarySystem.hideAllWindows();
			UIController.INSTANCE.init();
		} else if(currentAuth.name().equals(Auth.BOTH.toString())) {

		} else {
			throw new LoginException("Cannot Authorize");
		}
	}

	public void addMember(String memberId, String fname, String lname, String tel, Address addr) throws LibrarySystemException {

		MemberController memberController = new MemberController();
		//TODO: - Search if the member exists
		if (memberController.memberExists(memberId, allMemberIds())) {
			throw new LibrarySystemException("Member already existed.");
		}

		LibraryMember libraryMember = new LibraryMember(memberId, fname, lname, tel, addr);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveMember(libraryMember);
	}

	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws BookException {

		BookController bookController = new BookController();
		//TODO: - Search if the book exists
		if (bookController.bookExists(isbn, allBookIds())) {
			throw new BookException("Book already existed.");
		}

		Book book = new Book(isbn, title, maxCheckoutLength, authors);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveBook(book);
	}

	public void addCheckoutRecord(String memberId, BookCopy bookCopy, LocalDate checkoutDate, LocalDate dueDate) throws CheckoutRecordException {

		CheckoutController checkoutController = new CheckoutController();
		if (checkoutController.checkoutRecordExists(memberId, allBookIds())) {
			throw new CheckoutRecordException("Record already existed.");
		}

		CheckoutRecord checkoutRecord = new CheckoutRecord(memberId, bookCopy, checkoutDate, dueDate);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveCheckoutRecord(checkoutRecord);
	}

	public Book getBook(String isbn) throws BookException {
		BookController bookController = new BookController();
		if (!bookController.bookExists(isbn, allBookIds())) {
			throw new BookException("Book not found.");
		}

		return bookController.getBook(isbn);
	}

	public LibraryMember getMember(String memberId) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (!memberController.memberExists(memberId, allMemberIds())) {
			throw new LibrarySystemException("Member not found.");
		}

		return memberController.getMember(memberId);
	}

	public CheckoutRecord getCheckoutRecord(String memberId) throws CheckoutRecordException {
		CheckoutController checkoutController = new CheckoutController();
		if (!checkoutController.checkoutRecordExists(memberId, allMemberIds())) {
			throw new CheckoutRecordException("Checkout record not found.");
		}

		return checkoutController.getCheckoutRecord(memberId);
	}

	public void updateBook(Book book) throws BookException {
		BookController bookController = new BookController();
		if (!bookController.bookExists(book.getIsbn(), allBookIds())) {
			throw new BookException("Book not found.");
		}

		bookController.updateBook(book);
	}

	public void updateMember(LibraryMember libraryMember) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (!memberController.memberExists(libraryMember.getMemberId(), allMemberIds())) {
			throw new LibrarySystemException("Member was not updated.");
		}

		memberController.updateMember(libraryMember);
	}

	public List checkoutRecordsForTable() {

		return null;
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allCheckoutRecordIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readCheckoutRecordMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public HashMap<String, CheckoutRecord> allCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		return da.readCheckoutRecordMap();
	}

	@Override
	public HashMap<String, LibraryMember> getMembers() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}
}
