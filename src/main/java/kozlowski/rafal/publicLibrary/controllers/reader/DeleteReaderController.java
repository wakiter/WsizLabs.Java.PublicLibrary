package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.reader.DeleteReaderViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class DeleteReaderController {
    private final ReaderRepository readerRepository;

    public static final String DeleteReaderModalWindowUrl = "/reader/delete/";

    @ModelAttribute("deleteReader")
    public DeleteReaderViewModel deleteReaderViewModel() { return new DeleteReaderViewModel(); }

    public DeleteReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @RequestMapping(value = DeleteReaderModalWindowUrl, method = RequestMethod.POST)
    public String deleteBookModalWindow(
            @ModelAttribute("deleteReader") DeleteReaderViewModel viewModel) {
        var reader = readerRepository.findById(viewModel.getReaderId()).get();
        readerRepository.delete(reader);
        return "readers/list";
    }
}
