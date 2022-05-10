package kozlowski.rafal.publicLibrary.bootstrap;

import kozlowski.rafal.publicLibrary.model.Book;
import kozlowski.rafal.publicLibrary.model.Reader;
import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public BootStrapData(ReaderRepository readerRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Reader wakiter = new Reader("wakiter", "varez");
        Book theExpanse1 = new Book("The expanse book 1", "123123123");
        Book theExpanse2 = new Book("The expanse book 2", "321321321");

        //wakiter.addBook(theExpanse1);
        //wakiter.addBook(theExpanse2);
        //theExpanse1.borrow(wakiter);
        
        this.readerRepository.save(wakiter);
        this.bookRepository.save(theExpanse1);
        this.bookRepository.save(theExpanse2);

        System.out.println("Started in Boostrap");
    }
}
