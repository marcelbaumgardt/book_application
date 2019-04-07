package pl.marcelbaumgard.consdata.book_application.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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
    private long publishedDate;
    @Column(columnDefinition="TEXT")
    private String description;
    private int pageCount;
    private String thumbnail;
    private String language;
    private String previewLink;
    private double averageRating ;
    private String[] authors;
    private String[] categories;

}
