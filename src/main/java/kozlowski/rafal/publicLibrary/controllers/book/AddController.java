package kozlowski.rafal.publicLibrary.controllers.book;

import kozlowski.rafal.publicLibrary.model.Book;
import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import kozlowski.rafal.publicLibrary.viewModels.book.AddBookViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public final class AddController {
    private final BookRepository bookRepository;
    
    public static final String AddBookUrl = "/book/add";

    public AddController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("addBook")
    public AddBookViewModel addBookViewModel() { return new AddBookViewModel(); }

    @RequestMapping(value = AddBookUrl, method = RequestMethod.GET)
    public String addBook(
            @ModelAttribute("addBook") AddBookViewModel viewModel,
            ModelMap modelMap) {

        viewModel.setSubmitFormUrl(AddBookUrl);

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "addBook", modelMap.get("errors"));
        return "books/add";
    }

    @RequestMapping(value = AddBookUrl, method = RequestMethod.POST, params = "action=submit")
    public String addBook(
            @Valid @ModelAttribute("addBook") AddBookViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("addBook", viewModel);
            return "redirect:" + AddBookUrl;
        }

        addBook(viewModel);

        return "redirect:" + ListController.ListBooksUrl;
    }

    @RequestMapping(value = AddBookUrl, method = RequestMethod.POST, params = "action=cancel")
    public String addBookCancel() {
        return "redirect:" + ListController.ListBooksUrl;
    }

    private void addBook(AddBookViewModel viewModel) {
        var book = new Book(viewModel.getName(), viewModel.getUniversalIdentificationNumber());

        bookRepository.save(book);
    }
}
