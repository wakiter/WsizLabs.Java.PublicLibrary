package kozlowski.rafal.publicLibrary.viewModels.book;

import javax.validation.constraints.Size;

public class BookViewModelBase {

    private String submitFormUrl;

    @Size(min = 1, max = 500, message = "Długość nazwy musi być między 1 a 500 znaków!")
    private String name;

    @Size(min = 1, max = 500, message = "Długość uniwersalnego numeru identyfikacyjnego musi być między 1 a 500 znaków!")
    private String universalIdentificationNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversalIdentificationNumber() {
        return universalIdentificationNumber;
    }

    public void setUniversalIdentificationNumber(String universalIdentificationNumber) {
        this.universalIdentificationNumber = universalIdentificationNumber;
    }

    public String getSubmitFormUrl() {
        return submitFormUrl;
    }

    public void setSubmitFormUrl(String submitFormUrl) {
        this.submitFormUrl = submitFormUrl;
    }

}
