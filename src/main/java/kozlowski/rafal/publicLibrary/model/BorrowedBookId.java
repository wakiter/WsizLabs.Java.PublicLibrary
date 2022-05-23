package kozlowski.rafal.publicLibrary.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public final class BorrowedBookId implements Serializable {
    @Column(name = "reader_id")
    private Long readerId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "borrowTimestamp")
    private Date borrowTimestamp;

    public BorrowedBookId(Long readerId, Long bookId, Date borrowTimestamp) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowTimestamp = borrowTimestamp;
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

    public Date getBorrowTimestamp() { return borrowTimestamp; }

    public void setBorrowTimestamp(Date borrowTimestamp) { this.borrowTimestamp = borrowTimestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedBookId that = (BorrowedBookId) o;
        return Objects.equals(readerId, that.readerId)
                && Objects.equals(bookId, that.bookId)
                && Objects.equals(borrowTimestamp, that.borrowTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, bookId, borrowTimestamp);
    }
}
