package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.reader.BorrowedBookViewModel;
import kozlowski.rafal.publicLibrary.viewModels.reader.ReturnBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.stream.Collectors;

@Controller
public final class BorrowedBooksController {
    public final ReaderRepository readerRepository;

    public static final String ListBorrowedBooks = "/reader/listBorrowedBooks/";

    public static final String ReturnBorrowedBook = "/reader/return/";

    public BorrowedBooksController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @RequestMapping(value = ListBorrowedBooks + "{readerId}", method = RequestMethod.GET)
    public String listBorrowedBooks(
            @PathVariable Long readerId,
            Model model) {
        var reader = readerRepository.findById(readerId).get();
        var borrowedBooks = reader
                .getBorrows()
                .stream()
                .map(br -> new BorrowedBookViewModel(br.getBook().getId(), br.getBook().getName(), br.getId().getBorrowTimestamp(), br.getReturnTimestamp()))
                .collect(Collectors.toUnmodifiableList());

        model.addAttribute("borrowedBooks", borrowedBooks);
        model.addAttribute("readerId", readerId);

        return "readers/borrowedBooks";
    }

    @RequestMapping(value = ReturnBorrowedBook, method = RequestMethod.POST)
    public String returnABook(
            @ModelAttribute("returnBook") ReturnBookViewModel viewModel)
    {
        var reader = this.readerRepository
                .findById(viewModel.getReaderId())
                .get();

        var borrowBook = reader
                .getBorrows().stream()
                .filter(br -> br.getBook().getId() == viewModel.getBookId() && br.getReturnTimestamp() == null)
                .findFirst()
                .get();

        borrowBook.setReturnTimestamp(new Date());

        readerRepository.save(reader);

        return "readers/borrowedBooks";
    }
}
