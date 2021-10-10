package business.Controllers;

import business.Address;
import business.Book;
import business.Exceptions.LibrarySystemException;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.HashMap;
import java.util.List;

public class MemberController {
    public boolean memberExists(String memberId, List<String> memberIds) {
        DataAccessFacade da = new DataAccessFacade();
        return memberIds.contains(memberId);
    }
    public LibraryMember getMember(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		LibraryMember libraryMember = members.get(memberId);
		return libraryMember;
    }
    public void updateMember(LibraryMember libraryMember) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveMember(libraryMember);
    }
    public void deleteMember(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
        da.deleteMember(memberId);
    }
    public void addMember(String memberId,
                          String fname,
                          String lname,
                          String tel,
                          String street,
                          String city,
                          String state,
                          String zip) throws LibrarySystemException {
        Address address = new Address(street, city, state, zip);
        LibraryMember libraryMember = new LibraryMember(memberId, fname, lname, tel, address);
        DataAccess dataAccess = new DataAccessFacade();
        dataAccess.saveMember(libraryMember);
    }
}
