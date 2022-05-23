package kozlowski.rafal.publicLibrary.viewModels.reader;

import kozlowski.rafal.publicLibrary.viewModels.ViewModelBase;

import java.util.Date;

public final class BorrowedBookViewModel extends ViewModelBase {
    private Long bookId;

    private String bookName;

    private Date borrowTimestamp;

    private Date returnTimestamp;

    public BorrowedBookViewModel(Long bookId, String bookName, Date borrowTimestamp, Date returnTimestamp) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.borrowTimestamp = borrowTimestamp;
        this.returnTimestamp = returnTimestamp;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBorrowTimestamp() {
        return borrowTimestamp;
    }

    public void setBorrowTimestamp(Date borrowTimestamp) {
        this.borrowTimestamp = borrowTimestamp;
    }

    public Date getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(Date returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }
}
