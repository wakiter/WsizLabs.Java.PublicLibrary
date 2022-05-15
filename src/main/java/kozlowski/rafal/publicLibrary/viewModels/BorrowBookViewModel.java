package kozlowski.rafal.publicLibrary.viewModels;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public final class BorrowBookViewModel extends ViewModelBase {
    private Source source;

    @Min(value = 1, message = "Książka musi być wybrana!")
    private Long bookId;
    @Min(value = 1, message = "Czytelnik musi być wybrany!")
    private Long readerId;

    @NotNull(message = "Data wypożyczenia musi być podana!")
    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
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

    public Source getSource() { return source; }

    public void setSource(Source source) { this.source = source; }

    public enum Source {
        ByBook,
        ByReader
    }
}
