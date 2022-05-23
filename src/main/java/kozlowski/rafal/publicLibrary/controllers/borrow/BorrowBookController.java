package kozlowski.rafal.publicLibrary.controllers.borrow;

import kozlowski.rafal.publicLibrary.controllers.book.ListBookController;
import kozlowski.rafal.publicLibrary.controllers.reader.ListReaderController;
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
public final class BorrowBookController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public static final String BorrowABookUrl = "/borrow/";
    public static final String BorrowABookByBookUrl = BorrowABookUrl + "byBook/";

    public static final String BorrowABookByReaderUrl = BorrowABookUrl + "byReader/";

    public static final String ReturnTheBook = BorrowABookUrl + "return/";
    
    public BorrowBookController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @ModelAttribute("borrowBook")
    public BorrowBookViewModel borrowBookViewModel() {
        return new BorrowBookViewModel();
    }

    @RequestMapping(value = BorrowABookByBookUrl + "{bookId}", method = RequestMethod.GET)
    public String rentABook(
            @PathVariable Long bookId,
            Model model,
            @ModelAttribute("borrowBook") BorrowBookViewModel borrowBook,
            ModelMap modelMap) {
        borrowBook.setBookId(bookId);
        borrowBook.setSource(BorrowBookViewModel.Source.ByBook);
        borrowBook.setSubmitFormUrl(BorrowABookByBookUrl + bookId);

        var allReaders = readerRepository.findAll();
        var allBooks = bookRepository.findAll();

        var borrowedBooksNotReturned = StreamSupport.stream(allBooks.spliterator(), false)
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .get()
                .getBorrows()
                .stream()
                .filter(bb -> bb.getReturnTimestamp() == null)
                .collect(Collectors.toUnmodifiableSet());

        var chosenBookReaderIds = borrowedBooksNotReturned
                .stream()
                .map(bb -> bb.getReader().getId())
                .collect(Collectors.toUnmodifiableSet());

        var readersToDisplay = StreamSupport.stream(allReaders.spliterator(), false)
                        .filter(r -> !chosenBookReaderIds.contains(r.getId()))
                        .collect(Collectors.toList());

        model.addAttribute("selectedBookId", bookId);
        model.addAttribute("selectedAuthorId", -1);
        model.addAttribute("books", allBooks);
        model.addAttribute("readers", readersToDisplay);

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "borrowBook", modelMap.get("errors"));

        return "borrow/rent";
    }

    @RequestMapping(value = BorrowABookByBookUrl + "{bookId}", method = RequestMethod.POST, params = "action=submit")
    public String rentABook(
            @Valid @ModelAttribute("borrowBook") BorrowBookViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("borrowBook", viewModel);
            return "redirect:" + BorrowABookByBookUrl + viewModel.getBookId();
        }

        rentABook(viewModel);

        return "redirect:" + ListBookController.ListBooksUrl;
    }

    @RequestMapping(value = BorrowABookByBookUrl + "{bookId}", method = RequestMethod.POST, params = "action=cancel")
    public String rentABookCancel() {
        return "redirect:" + ListBookController.ListBooksUrl;
    }

    @RequestMapping(value = BorrowABookByReaderUrl + "{readerId}", method = RequestMethod.GET)
    public String rentABookByAuthor(
            @PathVariable Long readerId,
            Model model,
            @ModelAttribute("borrowBook") BorrowBookViewModel borrowBook,
            ModelMap modelMap) {
        borrowBook.setReaderId(readerId);
        borrowBook.setSource(BorrowBookViewModel.Source.ByReader);
        borrowBook.setSubmitFormUrl(BorrowABookByReaderUrl + readerId);

        var allReaders = readerRepository.findAll();
        var allBooks = bookRepository.findAll();
        var chosenReaderBookIds = StreamSupport.stream(allReaders.spliterator(), false)
                .filter(b -> b.getId() == readerId)
                .findFirst()
                .get()
                .getBorrows()
                .stream()
                .filter(br -> br.getReturnTimestamp() == null)
                .map(br -> br.getBook().getId())
                .collect(Collectors.toList());

        var booksToToDisplay = StreamSupport.stream(allBooks.spliterator(), false)
                .filter(r -> !chosenReaderBookIds.contains(r.getId()))
                .collect(Collectors.toList());

        model.addAttribute("selectedBookId", -1);
        model.addAttribute("selectedReaderId", readerId);
        model.addAttribute("books", booksToToDisplay);
        model.addAttribute("readers", allReaders);

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "borrowBook", modelMap.get("errors"));

        return "borrow/rent";
    }

    @RequestMapping(value = BorrowABookByReaderUrl + "{readerId}", method = RequestMethod.POST, params = "action=submit")
    public String rentABookByReader(
            @Valid @ModelAttribute("borrowBook") BorrowBookViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("borrowBook", viewModel);
            return "redirect:" + BorrowABookByReaderUrl + viewModel.getReaderId();
        }

        rentABook(viewModel);

        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    @RequestMapping(value = BorrowABookByReaderUrl + "{authorId}", method = RequestMethod.POST, params = "action=cancel")
    public String rentABookByReaderCancel() {
        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    private void rentABook(BorrowBookViewModel viewModel) {
        var book = bookRepository.findById(viewModel.getBookId()).get();
        var reader = readerRepository.findById(viewModel.getReaderId()).get();
        book.borrow(reader, viewModel.getBorrowDate());

        bookRepository.save(book);
    }
}
