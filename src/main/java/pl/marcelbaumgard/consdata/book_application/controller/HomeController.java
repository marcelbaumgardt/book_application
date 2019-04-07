package pl.marcelbaumgard.consdata.book_application.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.marcelbaumgard.consdata.book_application.model.Book;


@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String home() {
        return "index";
    }


    @GetMapping(path = "/error")
    public String error(){
        return "error";
    }


}
