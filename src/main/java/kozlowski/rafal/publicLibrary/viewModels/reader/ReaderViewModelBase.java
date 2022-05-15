package kozlowski.rafal.publicLibrary.viewModels.reader;

import kozlowski.rafal.publicLibrary.viewModels.ViewModelBase;

import javax.validation.constraints.Size;

public abstract class ReaderViewModelBase extends ViewModelBase {

    @Size(min = 1, max = 50, message = "Długość imienia musi być między 1 a 50 znaków!")
    private String firstname;

    @Size(min = 1, max = 50, message = "Długość nazwiska musi być między 1 a 50 znaków!")
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
