package kozlowski.rafal.publicLibrary.model;

import org.hibernate.annotations.Entity;

import javax.persistence.*;

@javax.persistence.Entity
public class BorrowedBook {

    @EmbeddedId
    private BorrowedBookId id = new BorrowedBookId();

    @ManyToOne
    @MapsId("readerId")
    private Reader reader;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    public BorrowedBook(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
    }

    public BorrowedBook() {
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BorrowedBookId getId() {
        return id;
    }

    public void setId(BorrowedBookId id) {
        this.id = id;
    }
}
