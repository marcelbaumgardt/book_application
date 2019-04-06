package pl.marcelbaumgard.consdata.book_application.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)
public class GBVolumeInfoWrapper {
    private String title;
    private String publisher;
    private String publishedDate;
    private String[] authors;
    private String description;
    private int pageCount;
    private int printType;
    private String[] categories;
    private Map<String, String> imageLinks = new HashMap<String, String>();
    private String language;

    public Map<String, String> getImageLinks() {
        return imageLinks;
    }
}
