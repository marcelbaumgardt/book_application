package pl.marcelbaumgard.book_application.errors;

/**
 * The type Book not found exception.
 */
public class BookNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Book not found exception.
     */
    public BookNotFoundException() {
        super("Book not found! Error 404");
    }
}
