package pl.marcelbaumgard.book_application.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.marcelbaumgard.book_application.model.Book;
import pl.marcelbaumgard.book_application.repository.BookRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * The type Book service test.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    /**
     * The Book repository.
     */
    @Mock
    BookRepository bookRepository;

    /**
     * The Book service.
     */
    BookService bookService;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        bookService=new BookService(bookRepository);
    }

    /**
     * Add book.
     *
     * @throws Exception the exception
     */
    @Test
    public void addBook() throws Exception {
        Book book= new Book();
        book.setIsbn("1234567890123");
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.addBook(book);
        String isbnBook=book.getIsbn();
        assertEquals(isbnBook,book.getIsbn());
    }

    /**
     * Gets all books.
     *
     * @throws Exception the exception
     */
    @Test
    public void getAllBooks() throws Exception {
        List<Book> bookList = Arrays.asList(new Book(), new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(bookList);
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(3,allBooks.size());
    }

    //TODO tests
//    @Test
//    public void getBook() throws Exception {
//
//
//
//   }
//    @Test
//    public void getBooksByCategory() throws Exception {
//
//    }
//    @Test
//    public void getAllBooksCategories() throws Exception {
//
//    }
//    @Test
//    public void getRatings() throws Exception {
//
//    }
}
