package kozlowski.rafal.publicLibrary.controllers;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.BorrowBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public final class BookController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BookController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        return "books/list";
    }

    @RequestMapping(value = "/rentABook/{bookId}", method = RequestMethod.GET)
    public String rentABook(@PathVariable Long bookId, Model model) {
        var borrowBook = new BorrowBookViewModel();
        borrowBook.setBookId(bookId);
        model.addAttribute("selectedBookId", bookId);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("borrowBook", borrowBook);
        model.addAttribute("readers", readerRepository.findAll());

        return "books/rent";
    }

    @RequestMapping(value = "/rentABook/{bookId}", method = RequestMethod.POST)
    public String rentABook(@PathVariable Long bookId, @Valid @ModelAttribute BorrowBookViewModel viewModel) {
    //    viewModel.

        return "";
    }
}
