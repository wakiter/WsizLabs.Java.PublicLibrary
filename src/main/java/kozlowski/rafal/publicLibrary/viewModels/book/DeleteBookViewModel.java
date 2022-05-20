package kozlowski.rafal.publicLibrary.viewModels.book;

import kozlowski.rafal.publicLibrary.viewModels.ViewModelBase;

public final class DeleteBookViewModel extends ViewModelBase {
    private Long bookId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
