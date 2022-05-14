package kozlowski.rafal.publicLibrary.controllers;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;

public class ReaderController {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public ReaderController(ReaderRepository readerRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }
}
