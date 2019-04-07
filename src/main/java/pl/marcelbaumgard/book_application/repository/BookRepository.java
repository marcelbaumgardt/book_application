package pl.marcelbaumgard.book_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcelbaumgard.book_application.model.Book;


import java.util.Optional;

/**
 * The interface Book repository.
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    /**
     * Find by isbn optional.
     *
     * @param isbn the isbn
     * @return the optional
     */
    Optional<Book> findByIsbn(String isbn);


}
