package kozlowski.rafal.publicLibrary.controllers;

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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public final class BookController {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public BookController(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    @ModelAttribute("borrowBook")
    public BorrowBookViewModel viewModel() {
        return new BorrowBookViewModel();
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        return "books/list";
    }

    @RequestMapping(value = "/rentABook/{bookId}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/rentABook/{bookId}", method = RequestMethod.POST, params = "action=submit")
    public String rentABook(
            @Valid @ModelAttribute("borrowBook") BorrowBookViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("borrowBook", viewModel);
            return "redirect:/rentABook/" + viewModel.getBookId();
        }

        rentABook(viewModel);

        return "redirect:/books";
    }

    @RequestMapping(value = "/rentABook/{bookId}", method = RequestMethod.POST, params = "action=cancel")
    public String rentABookCancel() {
        return "redirect:/books";
    }

    private void rentABook(BorrowBookViewModel viewModel) {
        var book = bookRepository.findById(viewModel.getBookId()).get();
        var reader = readerRepository.findById(viewModel.getReaderId()).get();
        book.borrow(reader);

        bookRepository.save(book);
    }
}
