package stepdefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.*;

public class StepDefinitions {
    public Actionwords actionwords = new Actionwords();

    @Given("^user has the necessary files$")
    public void userHasTheNecessaryFiles() {
        actionwords.userHasTheNecessaryFiles();
    }

    @When("^the user clicks the install button$")
    public void theUserClicksTheInstallButton() {
        actionwords.theUserClicksTheInstallButton();
    }

    @Then("^the program is installed in user's device$")
    public void theProgramIsInstalledInUsersDevice() {
        actionwords.theProgramIsInstalledInUsersDevice();
    }
}