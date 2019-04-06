package pl.marcelbaumgard.consdata.book_application.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndustryIdentifiers {
 private String type;
 private String identifier;

}
