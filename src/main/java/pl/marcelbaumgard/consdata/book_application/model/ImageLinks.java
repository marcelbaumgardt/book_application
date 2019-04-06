package pl.marcelbaumgard.consdata.book_application.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageLinks {
private String smallThumbnail;
private String thumbnail;
}
