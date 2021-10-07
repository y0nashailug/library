package business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;
import librarysystem.UIController;

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
			String[] menu = { "Add member", "Add book", "Add book copy", "Search member", "All book ids", "All member ids" };
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

		LibraryMember libraryMember = new LibraryMember(memberId, fname, lname, tel, addr);

		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.saveNewMember(libraryMember);
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
}
