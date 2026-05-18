package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfileEditModal extends BasePage {

    // Complex XPath selectors for the profile edit form fields and submit button
    private final By bioTextarea = By.xpath("//form[@id='sn-pm-edit-form']//textarea[@name='bio' and @id='sn_bio']");
    private final By submitButton = By.xpath("//form[@id='sn-pm-edit-form']//button[@type='submit' and contains(@class, 'sn-pm-btn-primary')]");
    private final By bioContainer = By.xpath("//div[@id='sn-pm-content']//div[contains(@class, 'sn-pm-body') and contains(@class, 'sn-pm-profile-view')]//div[@class='sn-pm-bio-box']");

    public ProfileEditModal(WebDriver driver) {
        super(driver);
    }

    /**
     * Updates the user profile biography text and submits the form.
     * @param newBio the new bio to set
     */
    public void updateProfileInformation(String newBio) {
        click(bioTextarea);
        writeText(bioTextarea, newBio);
        click(submitButton);
    }

    /**
     * Reads the saved biography text from the profile view modal wrapper.
     * @return Saved biography description string
     */
    public String getBioText() {
        return getText(bioContainer);
    }
}