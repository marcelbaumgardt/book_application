package pl.marcelbaumgard.book_application.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.marcelbaumgard.book_application.errors.BookNotFoundException;
import pl.marcelbaumgard.book_application.model.Book;
import pl.marcelbaumgard.book_application.service.BookService;


import java.util.*;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * The type Book controller test.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    /**
     * The Book service.
     */
    @Mock
    BookService bookService;
    /**
     * The Book controller.
     */
    @InjectMocks
    BookController bookController;

    /**
     * The Mock mvc.
     */
    MockMvc mockMvc;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).setControllerAdvice(new ControllerExceptionHandler()).build();

    }

    /**
     * Gets books.
     *
     * @throws Exception the exception
     */
    @Test
    public void getBooks() throws Exception {
        List<Book> books = Arrays.asList(new Book(), new Book(), new Book());
        Set<String> categories = new TreeSet<>();
        when(bookService.getAllBooks()).thenReturn(books);
        when(bookService.getAllBooksCategories()).thenReturn(categories);
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookMainView"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("categories"));


    }

    /**
     * Gets books by category.
     *
     * @throws Exception the exception
     */
    @Test
    public void getBooksByCategory() throws Exception {
        Book book=new Book();
        String[] categories=new String[]{"cars"};
        book.setCategories(categories);
        List<Book> books = Arrays.asList(book);
        Set<String> categoriesSet = new TreeSet<>();
        when(bookService.getBooksByCategory("cars")).thenReturn(books);
        when(bookService.getAllBooksCategories()).thenReturn(categoriesSet);

        mockMvc.perform(get("/books/cars"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookMainView"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("categories"));
    }

    /**
     * Book by categories not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void bookByCategoriesNotFoundException() throws Exception {

        when(bookService.getBooksByCategory(anyString())).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/books/abcdefg"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error404"));

    }

    /**
     * Gets book.
     *
     * @throws Exception the exception
     */
    @Test
    public void getBook() throws Exception {
        Book book = new Book();
        book.setIsbn("10000");


        when(bookService.getBook("10000")).thenReturn(book);


        mockMvc.perform(get("/book/10000"))
                .andExpect(status().isOk())
                .andExpect(view().name("singleBookView"))
                .andExpect(model().attributeExists("book"));


    }

    /**
     * Gets ratings.
     *
     * @throws Exception the exception
     */
    @Test
    public void getRatings() throws Exception {
        Map<String, LinkedList<Double>> ratings = new HashMap<>();

        when(bookService.getRatings()).thenReturn(ratings);

        mockMvc.perform(get("/rating"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorRatingView"))
                .andExpect(model().attributeExists("ratings"));


    }

    /**
     * Test user not found exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void bookNotFoundException() throws Exception {

        when(bookService.getBook(anyString())).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/book/4"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error404"));

    }
}
