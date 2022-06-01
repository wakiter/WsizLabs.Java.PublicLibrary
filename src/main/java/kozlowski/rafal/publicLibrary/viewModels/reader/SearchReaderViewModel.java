package kozlowski.rafal.publicLibrary.viewModels.reader;

import kozlowski.rafal.publicLibrary.viewModels.ViewModelBase;

public final class SearchReaderViewModel extends ViewModelBase {
    public static final String ReaderSurnameFieldName = "nazwisko_czytelnika";
    public static final String NumberOfNotReturnedBooks = "liczba_nieoddanych_ksiazek";
    public static final String BookTitle = "nazwa_ksiazki";
    public static final String BookNumber = "numer_ksiazki";

    public static final SearchField[] allFields = new SearchField[] {
            new SearchField("Nazwisko czytelnika", ReaderSurnameFieldName),
            new SearchField("Liczba nieoddanych książek", NumberOfNotReturnedBooks),
            new SearchField("Nazwa książki", BookTitle),
            new SearchField("Numer książki", BookNumber)
    };

    private String searchText = "";

    private String searchField = "";

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public SearchField[] getAllFields() {
        return SearchReaderViewModel.allFields;
    }
}
