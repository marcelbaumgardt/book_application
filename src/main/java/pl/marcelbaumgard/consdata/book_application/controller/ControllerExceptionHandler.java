package pl.marcelbaumgard.consdata.book_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.marcelbaumgard.consdata.book_application.errors.BookNotFoundException;


@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ModelAndView handleBookNotFound(Exception ex){

        ModelAndView modelAndView=new ModelAndView();

        log.error("Handlig Booker not fund exception");
        log.error(ex.getMessage());

        modelAndView.setViewName("error404");
        modelAndView.addObject("exception",ex);

        return modelAndView;
    }
}
