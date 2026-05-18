package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.components.HeaderComponent;
import utils.ConfigReader;

public class HomePage extends BasePage {

    // Home Page contains the header component, which includes both authenticated and unauthenticated elements
    public HeaderComponent header;

    // COMPLEX XPATH: Targets the exact paragraph inside the footer that holds the dynamic brand text context
    private final By copyrightParagraph = By.xpath(
            "//footer//p[contains(text(), 'Subtitle Ninjas')]"
    );

    // COMPLEX XPATH: Relational chaining targeting the explicit anchor link inside the facebook container
    private final By facebookLink = By.xpath(
            "//footer//p[@id='facebook-footer']/a[contains(@href, 'facebook.com')]"
    );

    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    /**
     * Navigate to the Home Page
     */
    public void navigateToHomePage() {
        driver.get(ConfigReader.getProperty("url"));

    }

    /**
     * Return the specific copyright text from the explicit paragraph node.
     * @return Text value of the copyright notice
     */
    public String getCopyrightText() {
        return getText(copyrightParagraph);
    }

    /**
     * Return the exact destination URL of the Facebook icon link.
     * @return The href attribute value string
     */
    public String getFacebookHref() {
        return waitForPresence(facebookLink).getAttribute("href");
    }

    /**
     * ADVANCED HOVER FEATURE: Performs a real mouse hover action over the Facebook circle icon.
     * This fulfills the advanced hover interaction requirement.
     */
    public void hoverOverFacebookIcon() {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibility(facebookLink)).perform();
    }
    
}