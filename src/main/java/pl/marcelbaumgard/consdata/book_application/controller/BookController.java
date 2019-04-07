package pl.marcelbaumgard.consdata.book_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcelbaumgard.consdata.book_application.model.Book;
import pl.marcelbaumgard.consdata.book_application.service.BookService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {

        this.bookService = bookService;
    }



    @GetMapping(path="/books")
    public String getUsers(Model model){
        List<Book> books = bookService.getAllBooks();
        Set<String> categorys =bookService.getAllBooksCategorys();
        model.addAttribute("books",books);
        model.addAttribute("categorys",categorys);

        return "bookMainView";
    }

    @GetMapping(path = "/book/{isbn}")
    public String getBook(@PathVariable(required = false) String isbn,Model model){
        Book book = bookService.getBook(isbn);
        if(book!=null) {
            model.addAttribute("book", book);
            return "singleBookView";
        }else{
            return "redirect:/error";
        }
    }

    @GetMapping(path="/raiting")
    public String getRaitings(Model model){
        Map<String, LinkedList<Double>> raitings = bookService.getRaitings();
        //TODO add all rates and divide
        Map<String, Double> recalculatedRaiting = raitings.entrySet().stream()
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().stream().mapToDouble(d->d.doubleValue()).average().getAsDouble()));
        System.out.println(recalculatedRaiting.toString());
        model.addAttribute("raitings",recalculatedRaiting);
        return "authorRaitingView";
    }



}
