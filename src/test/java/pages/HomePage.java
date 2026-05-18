package pages;

import org.openqa.selenium.WebDriver;

import pages.components.HeaderComponent;
import utils.ConfigReader;

public class HomePage extends BasePage {

    // Home Page contains the header component, which includes both authenticated and unauthenticated elements
    public HeaderComponent header;

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
    
}