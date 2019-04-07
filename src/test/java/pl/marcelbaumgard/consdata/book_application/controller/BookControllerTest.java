package pl.marcelbaumgard.consdata.book_application.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.marcelbaumgard.consdata.book_application.errors.BookNotFoundException;
import pl.marcelbaumgard.consdata.book_application.model.Book;
import pl.marcelbaumgard.consdata.book_application.service.BookService;


import java.util.*;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).setControllerAdvice(new ControllerExceptionHandler()).build();

    }

    @Test
    public void getBooks() throws Exception {
        List<Book> books = Arrays.asList(new Book(), new Book(), new Book());
        Set<String> categorys = new TreeSet<>();
        when(bookService.getAllBooks()).thenReturn(books);
        when(bookService.getAllBooksCategorys()).thenReturn(categorys);
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookMainView"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("categorys"));


    }

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

    @Test
    public void getRaitings() throws Exception {
        Map<String, LinkedList<Double>> raitings = new HashMap<>();

        when(bookService.getRaitings()).thenReturn(raitings);

        mockMvc.perform(get("/raiting"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorRaitingView"))
                .andExpect(model().attributeExists("raitings"));


    }

    @Test
    public void testUserNotFoundException() throws Exception {

        when(bookService.getBook("123")).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/book/4"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/error"));

    }
}
