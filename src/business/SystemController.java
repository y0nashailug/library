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
import business.Exceptions.LogoutException;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.*;

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

		String[] menu;
		if(currentAuth.name().equals(Auth.LIBRARIAN.toString())) {
			menu = Util.LIBRARIAN_MENU;
			UIController.INSTANCE.setItems(new ArrayList<>(Arrays.asList(menu)));
		} else if(currentAuth.name().equals(Auth.ADMIN.toString())) {
			menu = Util.ADMIN_MENU;
			UIController.INSTANCE.setItems(new ArrayList<>(Arrays.asList(menu)));
		} else if(currentAuth.name().equals(Auth.BOTH.toString())) {
			menu = Util.ALL_MENU;
			UIController.INSTANCE.setItems(new ArrayList<>(Arrays.asList(menu)));
		} else {
			throw new LoginException("Cannot Authorize");
		}

		LibrarySystem.hideAllWindows();
		UIController.INSTANCE.setTitle(Config.APP_NAME);
		UIController.INSTANCE.init();
		UIController.INSTANCE.setVisible(true);
	}

	public void logout() throws LogoutException {
		LibrarySystem.hideAllWindows();
		LoginWindow.INSTANCE.init();
		LoginWindow.INSTANCE.setVisible(true);
	}

	public void addMember(String memberId,
						  String fname,
						  String lname,
						  String tel,
						  String street,
						  String city,
						  String state,
						  String zip) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (memberController.memberExists(memberId, allMemberIds())) {
			throw new LibrarySystemException("Member already existed.");
		}

		Address address = new Address(street, city, state, zip);
		LibraryMember libraryMember = new LibraryMember(memberId, fname, lname, tel, address);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveMember(libraryMember);
	}

	public void addBook(String isbn, String title, int maxCheckoutLength, String[] autr, String[] addr) throws BookException {
		BookController bookController = new BookController();
		if (bookController.bookExists(isbn, allBookIds())) {
			throw new BookException("Book already existed.");
		}

		Address address = new Address(addr[0], addr[1], addr[2], addr[3]);
		Author author = new Author(autr[0], autr[1], autr[2], address, autr[3]);
		List<Author> authors = new ArrayList<>();
		authors.add(author);

		Book book = new Book(isbn, title, maxCheckoutLength, authors);
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveBook(book);
	}

	public CheckoutRecord addCheckoutRecord(LibraryMember libraryMember, Book book, LocalDate checkoutDate, LocalDate dueDate) throws CheckoutRecordException, LibrarySystemException, BookException {

		if (!book.isAvailable() || book.getNextAvailableCopy() == null) {
			throw new BookException("Book is not available.");
		}

		CheckoutRecord checkoutRecord;

		if (libraryMember.getCheckoutRecord() != null) {
			checkoutRecord = libraryMember.getCheckoutRecord();
		} else {
			checkoutRecord = new CheckoutRecord(libraryMember);
		}

		CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(book.getNextAvailableCopy(), checkoutDate, dueDate.plusDays(book.getMaxCheckoutLength()));
		checkoutRecord.addCheckoutRecordEntry(checkoutRecordEntry);

		DataAccess da = new DataAccessFacade();
		da.saveCheckoutRecord(checkoutRecord);

		libraryMember.setCheckoutRecord(checkoutRecord);

		updateMember(libraryMember);

		book.getNextAvailableCopy().changeAvailability();
		updateBook(book);

		return checkoutRecord;
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

		book.addCopy();
		bookController.updateBook(book);
	}

	public void updateMember(LibraryMember libraryMember) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (!memberController.memberExists(libraryMember.getMemberId(), allMemberIds())) {
			throw new LibrarySystemException("Member was not updated.");
		}

		memberController.updateMember(libraryMember);
	}

	public void deleteMember(String memberId) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (!memberController.memberExists(memberId, allMemberIds())) {
			throw new LibrarySystemException("Member was not updated.");
		}

		memberController.deleteMember(memberId);
	}

	public void getAndUpdateMember(String memberId, String fname, String lname, String tel) throws LibrarySystemException {
		MemberController memberController = new MemberController();
		if (!memberController.memberExists(memberId, allMemberIds())) {
			throw new LibrarySystemException("Member was not updated.");
		}

		LibraryMember libraryMember = getMember(memberId);
		libraryMember.setFirstName(fname);
		libraryMember.setLastName(lname);
		libraryMember.setTelephone(tel);

		memberController.updateMember(libraryMember);
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
	public List<CheckoutRecord> allCheckoutRecordsByIsbn(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> records = da.readCheckoutRecordMap();
		List<CheckoutRecord> checkoutRecordList = new ArrayList<>();
		List<String> checkoutRecordIds = allCheckoutRecordIds();

		for (int i = 0; i < records.size(); i++) {
			CheckoutRecord checkoutRecord = records.get(checkoutRecordIds.get(i));
			for (int j = 0; j < checkoutRecord.getCheckoutRecordEntries().size(); j++) {
				LocalDate today = LocalDate.now();
				CheckoutRecordEntry checkoutRecordEntry = checkoutRecord.getCheckoutRecordEntries().get(j);
				if (checkoutRecordEntry.getBookCopy().getBook().getIsbn().equals(isbn)
						&& checkoutRecordEntry.getDueDate().compareTo(today) > 1) {
					checkoutRecordList.add(checkoutRecord);
				}
			}
		}

		return checkoutRecordList;
	}

	@Override
	public HashMap<String, LibraryMember> getMembers() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}
}
