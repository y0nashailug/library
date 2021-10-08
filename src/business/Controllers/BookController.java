package business.Controllers;

import business.Book;
import business.BookCopy;
import dataaccess.DataAccessFacade;

import java.util.List;

public class BookController {
    public boolean bookExists(String isbn, List<String> bookIsbnList) {
        return bookIsbnList.contains(isbn);
    }

    public Book getBook(String isbn) {
        DataAccessFacade da = new DataAccessFacade();
        return da.getBook(isbn);
    }

    public void updateBook(Book book) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveBook(book);
    }

    public void addNewBook(Book book) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveBook(book);
    }

}

