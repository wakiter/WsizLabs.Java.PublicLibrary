package kozlowski.rafal.publicLibrary.controllers.book;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.BorrowBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public final class RentController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public static final String RentABookUrl = "/book/rent/";
    public RentController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @ModelAttribute("borrowBook")
    public BorrowBookViewModel borrowBookViewModel() {
        return new BorrowBookViewModel();
    }

    @RequestMapping(value = RentABookUrl + "{bookId}", method = RequestMethod.GET)
    public String rentABook(
            @PathVariable Long bookId,
            Model model,
            @ModelAttribute("borrowBook") BorrowBookViewModel borrowBook,
            ModelMap modelMap) {
        borrowBook.setBookId(bookId);

        var allReaders = readerRepository.findAll();
        var allBooks = bookRepository.findAll();
        var chosenBookReaderIds = StreamSupport.stream(allBooks.spliterator(), false)
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .get()
                .getBorrows()
                .stream()
                .map(br -> br.getReader().getId())
                .collect(Collectors.toList());

        var readersToDisplay = StreamSupport.stream(allReaders.spliterator(), false)
                        .filter(r -> !chosenBookReaderIds.contains(r.getId()))
                        .collect(Collectors.toList());

        model.addAttribute("selectedBookId", bookId);
        model.addAttribute("books", allBooks);
        model.addAttribute("readers", readersToDisplay);

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "borrowBook", modelMap.get("errors"));

        return "books/rent";
    }

    @RequestMapping(value = RentABookUrl + "{bookId}", method = RequestMethod.POST, params = "action=submit")
    public String rentABook(
            @Valid @ModelAttribute("borrowBook") BorrowBookViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("borrowBook", viewModel);
            return "redirect:" + RentABookUrl + viewModel.getBookId();
        }

        rentABook(viewModel);

        return "redirect:" + ListController.ListBooksUrl;
    }

    @RequestMapping(value = RentABookUrl + "{bookId}", method = RequestMethod.POST, params = "action=cancel")
    public String rentABookCancel() {
        return "redirect:" + ListController.ListBooksUrl;
    }

    private void rentABook(BorrowBookViewModel viewModel) {
        var book = bookRepository.findById(viewModel.getBookId()).get();
        var reader = readerRepository.findById(viewModel.getReaderId()).get();
        book.borrow(reader, viewModel.getBorrowDate());

        bookRepository.save(book);
    }
}
