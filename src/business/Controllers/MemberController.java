package business.Controllers;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class MemberController {
    public boolean memberExists(String memberId, List<String> memberIds) {
        return memberIds.contains(memberId);
    }
    public LibraryMember getMember(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
        return da.getMember(memberId);
    }
    public void updateMember(LibraryMember member) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveMember(member);
    }
    public void deleteMember(String memberId) {
        DataAccessFacade da = new DataAccessFacade();
        da.deleteMember(memberId);
    }
}
