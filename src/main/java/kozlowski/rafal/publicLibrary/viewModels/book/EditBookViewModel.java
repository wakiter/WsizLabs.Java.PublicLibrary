package kozlowski.rafal.publicLibrary.viewModels.book;

import kozlowski.rafal.publicLibrary.viewModels.book.BookViewModelBase;

public final class EditBookViewModel extends BookViewModelBase {
    private Long bookId;

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() { return bookId; }
}
