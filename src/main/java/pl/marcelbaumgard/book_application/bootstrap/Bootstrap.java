package pl.marcelbaumgard.book_application.bootstrap;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.marcelbaumgard.book_application.model.Book;
import pl.marcelbaumgard.book_application.service.BookService;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Bootstrap.
 */
@Component
public class Bootstrap implements CommandLineRunner {

    private BookService bookService;
    /**
     * The Http headers.
     */
    HttpHeaders httpHeaders = new HttpHeaders();
    /**
     * The Rest template.
     */
    RestTemplate restTemplate = new RestTemplate();

    private static final String ISBN_13 = "ISBN_13";

    /**
     * Instantiates a new Bootstrap.
     *
     * @param bookService the book service
     */
    @Autowired
    public Bootstrap(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        saveJSON();

    }

    private void saveJSON() throws GeneralSecurityException, IOException {

        String query = "json/books.json";
        List<Volume> volumeList = getVolumeList(query);

        for (Volume volume : volumeList) {

            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

            String isbn13 = volumeInfo.getIndustryIdentifiers().stream()
                    .filter(identifier -> ISBN_13.equals(identifier.getType()))
                    .map(Volume.VolumeInfo.IndustryIdentifiers::getIdentifier)
                    .findFirst()
                    .orElse(volume.getId());


            String title = volumeInfo.getTitle();
            String subtitle = volumeInfo.getSubtitle();
            String publisher = volumeInfo.getPublisher();
            String publishDate = volumeInfo.getPublishedDate();
            String description = volumeInfo.getDescription();
            Integer pageCount = volumeInfo.getPageCount();

            String thumbnail = Optional.ofNullable(volumeInfo.getImageLinks()).map(Volume.VolumeInfo.ImageLinks::getThumbnail).orElse("");


            String language = volumeInfo.getLanguage();
            String previewLink = volumeInfo.getPreviewLink();
            Double averageRating = volumeInfo.getAverageRating();

            String[] authors = volumeInfo.getAuthors()!=null
                ?volumeInfo.getAuthors().stream().toArray(String[]::new)
                : new String[0];
            String[] categories = volumeInfo.getCategories()!=null
                ? volumeInfo.getCategories().stream().toArray(String[]::new)
                : new String[0];


            Long timestampFromDate = getTimestampFromDate(publishDate);


            Book book = new Book();
            book.setIsbn(isbn13);
            book.setTitle(title);
            book.setSubtitle(subtitle);
            book.setPublisher(publisher);
            book.setPublishedDate(timestampFromDate);
            book.setDescription(description);
            book.setPageCount(pageCount);
            book.setThumbnail(thumbnail);
            book.setLanguage(language);
            book.setPreviewLink(previewLink);
            book.setAverageRating(averageRating);
            book.setAuthors(authors);
            book.setCategories(categories);

            bookService.addBook(book);

        }


    }

    private List<Volume> getVolumeList(String query) throws GeneralSecurityException, IOException {
        JacksonFactory jsonFactory = new JacksonFactory();
        final Books books = new Books(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null);

        Books.Volumes.List list = books.volumes().list(query).setMaxResults(40L);
        Volumes volumes = list.execute();




        return volumes.getItems();

    }

    private long getTimestampFromDate(String publishDate) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<>();
        knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd"));
        knownPatterns.add(new SimpleDateFormat("yyyy"));
        knownPatterns.add(new SimpleDateFormat("yyyy-MM"));

        Date date = new Date();
        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                date = pattern.parse(publishDate);
            } catch (ParseException pe) {
                //TODO Log exception when wrong date format
            }
        }

        return date.getTime();
    }
}


