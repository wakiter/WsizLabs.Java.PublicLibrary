package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.reader.EditReaderViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public final class EditReaderController {
    private final ReaderRepository readerRepository;

    public static final String EditReaderUrl = "/reader/edit/";

    public EditReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @ModelAttribute("editReader")
    public EditReaderViewModel viewModel() { return new EditReaderViewModel(); }

    @RequestMapping(value = EditReaderUrl + "{readerId}", method = RequestMethod.GET)
    public String editTheReader(
            @PathVariable Long readerId,
            @ModelAttribute("editReader") EditReaderViewModel viewModel,
            ModelMap modelMap) {
        viewModel.setReaderId(readerId);
        viewModel.setSubmitFormUrl(EditReaderUrl + viewModel.getReaderId());

        var reader = readerRepository.findById(readerId).get();

        viewModel.setFirstname(reader.getFirstname());
        viewModel.setLastname(reader.getLastname());

        modelMap.put(BindingResult.MODEL_KEY_PREFIX + "editReader", modelMap.get("errors"));

        return "readers/edit";
    }

    @RequestMapping(value = EditReaderUrl + "{readerId}", method = RequestMethod.POST, params = "action=submit")
    public String editTheReader(
            @Valid @ModelAttribute("editReader") EditReaderViewModel viewModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            redirectAttributes.addFlashAttribute("editReader", viewModel);
            return "redirect:" + EditReaderUrl + viewModel.getReaderId();
        }

        editTheReader(viewModel);

        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    @RequestMapping(value = EditReaderUrl + "{readerId}", method = RequestMethod.POST, params = "action=cancel")
    public String editTheReaderCancel() {
        return "redirect:" + ListReaderController.ListReadersUrl;
    }

    private void editTheReader(EditReaderViewModel viewModel) {
        var reader = readerRepository.findById(viewModel.getReaderId()).get();
        reader.setFirstname(viewModel.getFirstname());
        reader.setLastname(viewModel.getLastname());

        readerRepository.save(reader);
    }
}
