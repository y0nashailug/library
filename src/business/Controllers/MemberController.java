package business.Controllers;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class MemberController {
    public boolean memberExists(String memberId, List<String> memberIds) {
        return memberIds.contains(memberId);
    }

    public void addNewMember(LibraryMember libraryMember) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveNewMember(libraryMember);
    }
}
