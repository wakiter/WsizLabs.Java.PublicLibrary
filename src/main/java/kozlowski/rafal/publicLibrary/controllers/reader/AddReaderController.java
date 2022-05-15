package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.model.Reader;
import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.reader.AddReaderViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public final class AddReaderController {
    private final ReaderRepository readerRepository;

    public static final String AddReaderUrl = "/reader/add";

    public AddReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @ModelAttribute("addReader")
    public AddReaderViewModel viewModel() { return new AddReaderViewModel(); }

    @RequestMapping(value = AddReaderUrl, method = RequestMethod.GET)
    public String addReader(
            @ModelAttribute("addReader") AddReaderViewModel viewModel,
            ModelMap modelMap) {

        viewModel.setSubmitFormUrl(AddReaderUrl);

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "addReader", modelMap.get("errors"));
        return "readers/add";
    }

    @RequestMapping(value = AddReaderUrl, method = RequestMethod.POST, params = "action=submit")
    public String addBook(
            @Valid @ModelAttribute("addReader") AddReaderViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("addReader", viewModel);
            return "redirect:" + AddReaderUrl;
        }

        addReader(viewModel);

        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    @RequestMapping(value = AddReaderUrl, method = RequestMethod.POST, params = "action=cancel")
    public String addReaderCancel() {
        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    private void addReader(AddReaderViewModel viewModel) {
        var reader = new Reader(viewModel.getFirstname(), viewModel.getLastname());

        readerRepository.save(reader);
    }
}
