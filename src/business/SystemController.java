package business;

import java.util.*;

import business.Controllers.BookController;
import business.Controllers.CheckoutController;
import business.Controllers.CheckoutRecordController;
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

		memberController.addMember(memberId, fname, lname, tel, street, city, state, zip);
	}

	public void addBook(String isbn, String title, int maxCheckoutLength, String[] autr, String[] addr) throws BookException {
		BookController bookController = new BookController();
		if (bookController.bookExists(isbn, allBookIds())) {
			throw new BookException("Book already existed.");
		}

		bookController.saveBook(isbn, title, maxCheckoutLength, autr, addr);
	}

	public CheckoutRecord addCheckoutRecord(LibraryMember libraryMember, Book book) throws CheckoutRecordException, LibrarySystemException, BookException {

		if (!book.isAvailable() || book.getNextAvailableCopy() == null) {
			throw new BookException("Book is not available.");
		}

		MemberController memberController = new MemberController();
		if (!memberController.memberExists(libraryMember.getMemberId(), allMemberIds())) {
			throw new LibrarySystemException("Member was not updated.");
		}

		BookController bookController = new BookController();
		if (!bookController.bookExists(book.getIsbn(), allBookIds())) {
			throw new BookException("Book not found.");
		}

		CheckoutRecord checkoutRecord = libraryMember.getCheckoutRecord();

		BookCopy bookCopy = book.getNextAvailableCopy();
		CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(bookCopy);
		checkoutRecord.addCheckoutRecordEntry(checkoutRecordEntry);

		// Save checkout record
		CheckoutRecordController checkoutRecordController = new CheckoutRecordController();
		checkoutRecordController.addCheckout(libraryMember.getMemberId(), checkoutRecord);

		// Update member
		libraryMember.setCheckoutRecord(checkoutRecord);
		memberController.updateMember(libraryMember);

		// Update book
		book.getNextAvailableCopy().changeAvailability();
		bookController.updateBook(book);

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

	public void addBookCopy(Book book) throws BookException {
		BookController bookController = new BookController();
		if (!bookController.bookExists(book.getIsbn(), allBookIds())) {
			throw new BookException("Book not found.");
		}

		book.addCopy();
		bookController.updateBook(book);
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
			throw new LibrarySystemException("Member not found.");
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
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public HashMap<String, LibraryMember> getMembers() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}
}
