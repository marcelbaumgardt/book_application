package pl.marcelbaumgard.consdata.book_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GBItemsWrapper {
    private Book volumeInfo;
    public Book getVolumeInfo() {
        return volumeInfo;
    }
    public void setVolumeInfo(Book volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
