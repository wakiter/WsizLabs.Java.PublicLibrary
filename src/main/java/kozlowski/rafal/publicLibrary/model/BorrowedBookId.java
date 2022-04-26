package kozlowski.rafal.publicLibrary.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BorrowedBookId implements Serializable {
    @Column(name = "reader_id")
    private Long readerId;

    @Column(name = "book_id")
    private Long bookId;

    public BorrowedBookId(Long readerId, Long bookId) {
        this.readerId = readerId;
        this.bookId = bookId;
    }

    public BorrowedBookId() {
    }

    public Long getReaderId() {
        return readerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedBookId that = (BorrowedBookId) o;
        return Objects.equals(readerId, that.readerId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, bookId);
    }
}
