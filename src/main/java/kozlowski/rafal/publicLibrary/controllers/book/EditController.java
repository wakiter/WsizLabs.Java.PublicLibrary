package kozlowski.rafal.publicLibrary.controllers.book;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.viewModels.book.EditBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public final class EditController {

    private final BookRepository bookRepository;

    public static final String EditBookUrl = "/book/edit/";

    public EditController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("editBook")
    public EditBookViewModel editBookViewModel() {return new EditBookViewModel(); }

    @RequestMapping(value = EditBookUrl + "{bookId}", method = RequestMethod.GET)
    public String editTheBook(
            @PathVariable Long bookId,
            Model model,
            @ModelAttribute("editBook") EditBookViewModel viewModel,
            ModelMap modelMap) {
        viewModel.setBookId(bookId);
        viewModel.setSubmitFormUrl(EditBookUrl + viewModel.getBookId().toString());

        var book = bookRepository.findById(bookId).get();

        viewModel.setName(book.getName());
        viewModel.setUniversalIdentificationNumber(book.getUniversalIdentificationNumber());

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "editBook", modelMap.get("errors"));

        return "books/edit";
    }
    
    @RequestMapping(value = EditBookUrl + "{bookId}", method = RequestMethod.POST, params = "action=submit")
    public String editTheBook(
        @Valid @ModelAttribute("editBook") EditBookViewModel viewModel,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("editBook", viewModel);
            return "redirect:" + EditBookUrl + viewModel.getBookId();
        }

        editTheBook(viewModel);

        return "redirect:" + ListController.ListBooksUrl;
    }

    @RequestMapping(value = EditBookUrl + "{bookId}", method = RequestMethod.POST, params = "action=cancel")
    public String editTheBookCancel() {
        return "redirect:" + ListController.ListBooksUrl;
    }

    private void editTheBook(EditBookViewModel viewModel) {
        var book = bookRepository.findById(viewModel.getBookId()).get();
        book.setName(viewModel.getName());
        book.setUniversalIdentificationNumber(viewModel.getUniversalIdentificationNumber());

        bookRepository.save(book);
    }
}
