package kozlowski.rafal.publicLibrary.viewModels;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public final class BorrowBookViewModel {
    @Min(value = 1, message = "Book should be chosen!")
    private Long bookId;
    @Min(value = 1, message = "Reader should be chosen!")
    private Long readerId;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    private Date borrowDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
