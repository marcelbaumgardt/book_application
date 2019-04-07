package pl.marcelbaumgard.consdata.book_application.errors;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found! Error 404");
    }
}
