package pl.marcelbaumgard.consdata.book_application.bootstrap;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.BooksVolumesRecommendedRateResponse;
import com.google.api.services.books.model.RequestAccess;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.marcelbaumgard.consdata.book_application.model.Book;
import pl.marcelbaumgard.consdata.book_application.model.GBItemsWrapper;
import pl.marcelbaumgard.consdata.book_application.model.GBWrapper;
import pl.marcelbaumgard.consdata.book_application.service.BookService;
import sun.net.www.http.HttpClient;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    private BookService bookService;

    @Autowired
    public Bootstrap(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        //loadBooksFromJSONToDatabase();
        getGoogleDetailISBN();

    }
    public void getGoogleDetailISBN(){
        String numberOfRecords = "40";//MAX 40
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GBWrapper> entity = restTemplate.getForEntity("https://www.googleapis.com/books/v1/volumes?q=java&maxResults="+numberOfRecords, GBWrapper.class);

        GBItemsWrapper[] items = entity.getBody().getItems();
        for(GBItemsWrapper item:items){
            Book book = item.getVolumeInfo();
            System.out.println(book.toString());
            bookService.addBook(book);
        }



    }

    private void loadBooksFromJSONToDatabase() {


        ObjectMapper mapper = new ObjectMapper();
        TypeReference<GBWrapper> typeReference = new TypeReference<GBWrapper>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/books.json");


        try {
            GBWrapper wrapper = mapper.readValue(inputStream, typeReference);
            GBItemsWrapper[] items = wrapper.getItems();
            for(GBItemsWrapper item:items){
                Book book = item.getVolumeInfo();
                System.out.println(book.toString());
                bookService.addBook(book);
            }
            System.out.println("Books Saved!");
        } catch (IOException e) {
            System.out.println("Unable to save books: " + e.getMessage());
        }

    }



}


