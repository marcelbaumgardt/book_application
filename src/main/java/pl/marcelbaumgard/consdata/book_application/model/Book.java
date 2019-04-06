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
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    //private Map<String, String> industryIdentifiers = new HashMap<String, String>();
    @JsonIgnore
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    //TODO convert to timestamp
    //@JsonFormat( shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    private String publishedDate;
    @Column(columnDefinition="TEXT")
    private String description;
    private int pageCount;
    //
    @JsonIgnore
    private String thumbnailUrl="https://podarunkowo.pl/6538-large_default/duza-ksiazka-na-alkohol-pan-tadeusz.jpg";
//    private Map<String, String> imageLinks = new HashMap<String, String>();
    private String language;
    private String previewLink;
    private double averageRating ;
    private String[] authors;
    private String[] categories;

}
