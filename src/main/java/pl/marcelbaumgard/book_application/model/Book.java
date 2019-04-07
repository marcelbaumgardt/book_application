package pl.marcelbaumgard.book_application.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * The type Book.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private Long publishedDate;
    @Column(columnDefinition="TEXT")
    private String description;
    private Integer pageCount;
    private String thumbnail;
    private String language;
    private String previewLink;
    private Double averageRating ;
    private String[] authors;
    private String[] categories;

}
