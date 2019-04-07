package pl.marcelbaumgard.book_application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.marcelbaumgard.book_application.errors.BookNotFoundException;


/**
 * The type Controller exception handler.
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    /**
     * Handle book not found model and view.
     *
     * @param ex the ex
     * @return the model and view
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ModelAndView handleBookNotFound(Exception ex){

        ModelAndView modelAndView=new ModelAndView();

        log.error("Handling Booker not fund exception");
        log.error(ex.getMessage());

        modelAndView.setViewName("error404");
        modelAndView.addObject("exception",ex);

        return modelAndView;
    }
}
