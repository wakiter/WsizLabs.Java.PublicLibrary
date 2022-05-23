package kozlowski.rafal.publicLibrary.model;

import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
public final class BorrowedBook {

    @EmbeddedId
    private BorrowedBookId id = new BorrowedBookId();

    @ManyToOne
    @MapsId("readerId")
    private Reader reader;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnTimestamp;

    public BorrowedBook(Reader reader, Book book, Date borrowTimestamp, Date returnTimestamp) {
        this.reader = reader;
        this.book = book;
        this.returnTimestamp = returnTimestamp;

        this.id.setBookId(this.book.getId());
        this.id.setReaderId(this.reader.getId());
        this.id.setBorrowTimestamp(borrowTimestamp);
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

    public Date getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(Date returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }
}
