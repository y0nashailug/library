package business.Controllers;

import business.Address;
import business.Author;
import business.Book;
import business.Exceptions.BookException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookController {
    public boolean bookExists(String isbn, List<String> bookIsbnList) {
        return bookIsbnList.contains(isbn);
    }

    public Book getBook(String isbn) {
        DataAccessFacade da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBookMap();
		Book book = books.get(isbn);
		return book;
    }

    public void updateBook(Book book) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveBook(book);
    }

    public void addNewBook(Book book) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveBook(book);
    }

    public void saveBook(String isbn, String title, int maxCheckoutLength, String[] autr, String[] addr) throws BookException {
        Address address = new Address(addr[0], addr[1], addr[2], addr[3]);
        Author author = new Author(autr[0], autr[1], autr[2], address, autr[3]);
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Book book = new Book(isbn, title, maxCheckoutLength, authors);
        DataAccess dataAccess = new DataAccessFacade();
        dataAccess.saveBook(book);
    }

}

