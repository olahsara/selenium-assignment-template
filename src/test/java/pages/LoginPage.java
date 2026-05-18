package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Form field locators using complex XPath expressions
    private final By usernameField = By.xpath(
            "//form[@id='loginform']//input[@id='user_login' and @name='log']"
    );
    private final By passwordField = By.xpath(
            "//form[@id='loginform']//input[@id='user_pass' and @type='password']"
    );

    private final By submitButton = By.xpath(
            "//form[@id='loginform']//input[@type='submit' and @id='wp-submit']"
    );

    // Recaptcha
    private final By recaptchaResponseField = By.id("g-recaptcha-response");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fill in the login credentials, wait for the reCAPTCHA token, and submit the form.
     * @param username the uer username
     * @param password the user password
     */
    public void login(String username, String password) {
        writeText(usernameField, username); 
        writeText(passwordField, password);

        // Dynamic synchronization layer waiting for manual reCAPTCHA solving
        try {
            System.out.println("[SELENIUM] Please solve the reCAPTCHA verification in the browser window...");
            waitForCustomCondition(d -> {
                String captchaToken = d.findElement(recaptchaResponseField).getAttribute("value");
                return captchaToken != null && !captchaToken.isEmpty();
            });
            System.out.println("[SELENIUM] reCAPTCHA completed successfully, continuing workflow...");
        } catch (Exception e) {
            System.out.println("[WARNING] Synchronization timeout reached for reCAPTCHA validation.");
        }

        click(submitButton);                
    }
}