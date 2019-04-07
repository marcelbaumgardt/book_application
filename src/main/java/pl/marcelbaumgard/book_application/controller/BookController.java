package pl.marcelbaumgard.book_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.marcelbaumgard.book_application.model.Book;
import pl.marcelbaumgard.book_application.service.BookService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Book controller.
 */
@Controller
public class BookController {

    /**
     * The Book service.
     */
    BookService bookService;

    /**
     * Instantiates a new Book controller.
     *
     * @param bookService the book service
     */
    @Autowired
    public BookController(BookService bookService) {

        this.bookService = bookService;
    }


    /**
     * Gets users.
     *
     * @param model    the model
     * @param category the category
     * @return the users
     */
    @GetMapping(path = "/books")
    public String getUsers(Model model, @RequestParam(value = "category", required = false) String category) {
        List<Book> books = category == null
                ? bookService.getAllBooks()
                : bookService.getBooksByCategory(category);

        Set<String> booksCategories = bookService.getAllBooksCategories();


        model.addAttribute("books", books);
        model.addAttribute("categories", booksCategories);

        return "bookMainView";
    }


    /**
     * Gets book.
     *
     * @param isbn  the isbn
     * @param model the model
     * @return the book
     */
    @GetMapping(path = "/book/{isbn}")
    public String getBook(@PathVariable(required = false) String isbn, Model model) {
        Book book = bookService.getBook(isbn);
        if (book != null) {
            model.addAttribute("book", book);
            return "singleBookView";
        } else {
            return "redirect:/error";
        }
    }

    /**
     * Get ratings string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping(path = "/rating")
    public String getRatings(Model model) {
        Map<String, LinkedList<Double>> ratings = bookService.getRatings();
        //TODO add all rates and divide
        Map<String, Double> recalculatedRating = ratings.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().stream().mapToDouble(Double::doubleValue).average().getAsDouble()));
        model.addAttribute("ratings", recalculatedRating);
        return "authorRatingView";
    }


}
