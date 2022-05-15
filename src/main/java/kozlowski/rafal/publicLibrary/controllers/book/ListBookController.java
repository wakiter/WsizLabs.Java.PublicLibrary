package kozlowski.rafal.publicLibrary.controllers.book;

import kozlowski.rafal.publicLibrary.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class ListBookController {
    private final BookRepository bookRepository;
    
    public static final String ListBooksUrl = "/book/list";
    
    public ListBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(ListBooksUrl)
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        return "books/list";
    }
}
