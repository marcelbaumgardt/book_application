package pl.marcelbaumgard.book_application.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * The type Home controller.
 */
@Controller
public class HomeController {

    /**
     * Home string.
     *
     * @return the string
     */
    @GetMapping(path = "/")
    public String home() {
        return "index";
    }


    /**
     * Error string.
     *
     * @return the string
     */
    @GetMapping(path = "/error")
    public String error(){
        return "error";
    }


}
