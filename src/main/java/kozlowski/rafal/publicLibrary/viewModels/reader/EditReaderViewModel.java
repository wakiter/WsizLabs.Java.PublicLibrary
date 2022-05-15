package kozlowski.rafal.publicLibrary.viewModels.reader;

public final class EditReaderViewModel extends ReaderViewModelBase {
    private Long readerId;

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }
}
