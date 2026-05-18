package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.LoginPage;
import pages.ProfileEditModal;

public class HeaderComponent extends BasePage {

    // Unauthenticated elements
    // COMPLEX XPATH:
    // Fully chained complex XPath matching the public auth state container structure
    private final By loginLink = By.xpath("//div[contains(@class, 'sn-header__actions')]//div[@data-sn-auth-public]//a[@data-sn-auth-link='login' and contains(@class, 'sn-auth-link--login')]");
    
    // Authenticated elements
    // COMPLEX XPATH: 
    // Enhanced complexity matching the private account avatar buttons
    private final By profileAvatarButton = By.xpath("//div[contains(@class, 'sn-header__actions')]//div[@data-sn-auth-private]//button[contains(@class, 'sn-account__avatar') and @type='button']");
    
    // COMPLEX XPATH:
    // Complex dropdown navigation chaining
    private final By profileEditLink = By.xpath("//div[contains(@class, 'sn-account') and contains(@class, 'sn-dropdown')]//ul[contains(@class, 'sn-dropdown__menu')]//a[@data-target='edit_profile']");
    
    // COMPLEX XPATH:
    // Complex logical pairing for the logout action link
    private final By logoutButton = By.xpath("//div[contains(@class, 'sn-account') and contains(@class, 'sn-dropdown')]//ul[contains(@class, 'sn-dropdown__menu')]//a[@data-sn-auth-link='logout' or contains(@href, 'kilepes')]");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickLogin() {
        click(loginLink);
        return new LoginPage(driver);
    }

    /**
     * Ensures that the user is logged in by checking that the profile avatar is visible
     *
     * @return user is logged in or not
     */
    public boolean isUserLoggedIn() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(profileAvatarButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Opens the profile dropdown menu by clicking on the profile avatar
     */
    public void openAvatarMenu() {
        click(profileAvatarButton);
    }

    /**
     * Clicks the profile edit link in the dropdown menu to open the profile edit modal
     *
     * @return a ProfileEditModal object
     */
    public ProfileEditModal goToProfileEdit() {
        openAvatarMenu();
        click(profileEditLink);
        return new ProfileEditModal(driver);

    }

    /**
     * Clicks the logout button in the dropdown menu to log out the user
     */
    public void logout() {
        openAvatarMenu();
        click(logoutButton);
    }
}