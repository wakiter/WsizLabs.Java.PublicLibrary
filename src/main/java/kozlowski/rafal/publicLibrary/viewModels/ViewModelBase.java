package kozlowski.rafal.publicLibrary.viewModels;

public abstract class ViewModelBase {
    private String submitFormUrl;

    public String getSubmitFormUrl() {
        return submitFormUrl;
    }

    public void setSubmitFormUrl(String submitFormUrl) {
        this.submitFormUrl = submitFormUrl;
    }
}