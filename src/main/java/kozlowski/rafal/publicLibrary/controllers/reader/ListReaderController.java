package kozlowski.rafal.publicLibrary.controllers.reader;

import kozlowski.rafal.publicLibrary.repositories.ReaderRepository;
import kozlowski.rafal.publicLibrary.viewModels.reader.SearchReaderViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;

@Controller
public final class ListReaderController {
    private final ReaderRepository readerRepository;

    public static final String ListReadersUrl = "/reader/list";

    public ListReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @RequestMapping(ListReadersUrl)
    public String getReaders(
            @RequestParam(required = false) String searchField,
            @RequestParam(required = false) String searchText,
            Model model) {

        var searchModel = new SearchReaderViewModel();
        model.addAttribute("search", searchModel);

        searchModel.setSearchText(searchText == null ? "" : searchText);
        searchModel.setSearchField(searchField == null ? "" : searchField);

        if (StringUtils.isEmptyOrWhitespace(searchText)
                || StringUtils.isEmptyOrWhitespace(searchField)
                || !Arrays.stream(SearchReaderViewModel.allFields).anyMatch(f -> f.getValue().equals(searchField)))
        {
            model.addAttribute("readers", readerRepository.findAll());
        }
        else
        {
            switch(searchField)
            {
                case SearchReaderViewModel.ReaderSurnameFieldName:
                    model.addAttribute("readers", readerRepository.findByLastnameContainsIgnoreCase(searchText));
                    break;
                case SearchReaderViewModel.BookNumber:
                    model.addAttribute("readers", readerRepository.findByBooksBookUniversalIdentificationNumberContainsIgnoreCase(searchText));
                    break;
                case SearchReaderViewModel.BookTitle:
                    model.addAttribute("readers", readerRepository.findByBooksBookNameContainsIgnoreCase(searchText));
                    break;
                case SearchReaderViewModel.NumberOfNotReturnedBooks:
                    var count = parseIntOrNull(searchText);
                    if (count == null) {
                        //todo: add error message
                        break;
                    }

                    if (count <= 0)
                    {
                        model.addAttribute("readers", readerRepository.findAll());
                    }
                    else
                    {
                        model.addAttribute("readers", readerRepository.findByCountOfNotReturnedBooks(count));
                    }
                    break;
                default:
                    model.addAttribute("readers", readerRepository.findAll());
            }
        }

        return "readers/list";
    }

    private static Long parseIntOrNull(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
