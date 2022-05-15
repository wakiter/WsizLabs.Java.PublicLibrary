package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class ListReaderController {
    private final ReaderRepository readerRepository;

    public static final String ListReadersUrl = "/reader/list";

    public ListReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @RequestMapping(ListReadersUrl)
    public String getReaders(Model model) {
        model.addAttribute("readers", readerRepository.findAll());

        return "readers/list";
    }
}
