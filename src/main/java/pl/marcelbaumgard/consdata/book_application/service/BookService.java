package pl.marcelbaumgard.consdata.book_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcelbaumgard.consdata.book_application.errors.BookNotFoundException;
import pl.marcelbaumgard.consdata.book_application.model.Book;
import pl.marcelbaumgard.consdata.book_application.repository.BookRepository;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class BookService {

    BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void addBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {

        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Book getBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(()->new BookNotFoundException());
        return book;

    }


    public Set<String> getAllBooksCategorys() {
        List<Book> books = bookRepository.findAll();
        Set<String> categorys=new HashSet<>();
        for(Book book:books){
            if(book.getCategories()!=null) {
                categorys.addAll(Arrays.asList(book.getCategories()));
            }
        }
        return categorys;
    }

    public Map<String, LinkedList<Double>> getRaitings() {
        List<Book> books = bookRepository.findAll();
        Map<String,LinkedList<Double>> raitngs=new TreeMap<>();
        for(Book book:books){
            if(book.getAuthors()!=null && book.getAverageRating()!=0) {
                List<String> authors = Arrays.asList(book.getAuthors());
                for(String author:authors){
                        if(raitngs.containsKey(author)){
                            LinkedList<Double> rates = raitngs.get(author);
                            rates.add(book.getAverageRating());
                            raitngs.put(author,rates);

                        }else{
                            LinkedList<Double> rates=new LinkedList<>();
                            rates.add(book.getAverageRating());
                            raitngs.put(author,rates);
                        }
                }
            }

        }
        return raitngs;
    }
}
