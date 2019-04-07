package pl.marcelbaumgard.book_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcelbaumgard.book_application.errors.BookNotFoundException;
import pl.marcelbaumgard.book_application.model.Book;
import pl.marcelbaumgard.book_application.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Book service.
 */
@Service
public class BookService {

    /**
     * The Book repository.
     */
    BookRepository bookRepository;


    /**
     * Instantiates a new Book service.
     *
     * @param bookRepository the book repository
     */
    @Autowired
    public BookService(BookRepository bookRepository) {

        this.bookRepository = bookRepository;
    }

    /**
     * Add book.
     *
     * @param book the book
     * @return the book
     */
    public Book addBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    /**
     * Gets all books.
     *
     * @return the all books
     */
    public List<Book> getAllBooks() {

        return bookRepository.findAll();

    }

    /**
     * Gets book.
     *
     * @param isbn the isbn
     * @return the book
     */
    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);


    }

    /**
     * Gets books by category.
     *
     * @param category the category
     * @return the books by category
     */
    public List<Book> getBooksByCategory(String category) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
        .filter(book -> Arrays.stream(book.getCategories())
        .anyMatch(bookCategory -> bookCategory.equals(category)))
        .collect(Collectors.toList());
        
    }


    /**
     * Gets all books categories.
     *
     * @return the all books categories
     */
    public Set<String> getAllBooksCategories() {
        List<Book> books = bookRepository.findAll();
        Set<String> categories = new HashSet<>();
        for (Book book : books) {
            if (book.getCategories() != null) {
                categories.addAll(Arrays.asList(book.getCategories()));
            }
        }
        return categories;
    }

    /**
     * Gets ratings.
     *
     * @return the ratings
     */
    public Map<String, LinkedList<Double>> getRatings() {
        List<Book> books = bookRepository.findAll();
        Map<String, LinkedList<Double>> ratings = new TreeMap<>();
        for (Book book : books) {
            if (book.getAuthors() != null && book.getAverageRating() != null && book.getAverageRating() != 0) {

                List<String> authors = Arrays.asList(book.getAuthors());
                for (String author : authors) {
                    if (ratings.containsKey(author)) {
                        LinkedList<Double> rates = ratings.get(author);
                        rates.add(book.getAverageRating());
                        ratings.put(author, rates);

                    } else {
                        LinkedList<Double> rates = new LinkedList<>();
                        rates.add(book.getAverageRating());
                        ratings.put(author, rates);
                    }
                }
            }

        }
        return ratings;
    }
}
