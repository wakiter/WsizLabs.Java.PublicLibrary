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

        wakiter.addBook(theExpanse1);
        //theExpanse1.borrow(wakiter);
        
        this.readerRepository.save(wakiter);
        this.bookRepository.save(theExpanse1);

        System.out.println("Started in Boostrap");
    }
}
