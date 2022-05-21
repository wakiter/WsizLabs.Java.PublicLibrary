package kozlowski.rafal.publicLibrary.controllers.book;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.viewModels.book.DeleteBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public final class DeleteBookController {
    private final BookRepository bookRepository;

    public static final String DeleteBookModalWindowUrl = "/book/delete/";

    @ModelAttribute("deleteBook")
    public DeleteBookViewModel deleteBookViewModel() { return new DeleteBookViewModel(); }

    public DeleteBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = DeleteBookModalWindowUrl, method = RequestMethod.POST)
    public String deleteBookModalWindow(
            @ModelAttribute("deleteBook") DeleteBookViewModel viewModel) {
        var book = bookRepository.findById(viewModel.getBookId()).get();
        bookRepository.delete(book);
        return "books/list";
    }
}
