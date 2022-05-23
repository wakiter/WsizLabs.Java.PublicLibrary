package kozlowski.rafal.publicLibrary.viewModels.reader;

import kozlowski.rafal.publicLibrary.viewModels.ViewModelBase;

public final class ReturnBookViewModel extends ViewModelBase {
    private Long bookId;

    private Long readerId;

    public ReturnBookViewModel(Long bookId, Long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
    }

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
}
