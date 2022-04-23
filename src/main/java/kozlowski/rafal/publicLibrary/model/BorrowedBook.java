package kozlowski.rafal.publicLibrary.model;

import org.hibernate.annotations.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Reader reader;

    @ManyToOne
    private Book book;

    public BorrowedBook(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
    }

    public BorrowedBook() {
    }
}
