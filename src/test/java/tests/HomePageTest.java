package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest extends BaseTest {

    @Test(description = "Verify the home page title using getTitle()")
    public void verifyPageTitle() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Subtitle Ninjas"),
                "Page title verification failed: Target string not found in title.");
    }

    @Test(description = "Verify static text content and element presence on the homepage")
    public void verifyStaticContent() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        String copyrightText = homePage.getCopyrightText();
        Assert.assertTrue(copyrightText.contains("Subtitle Ninjas"),
                "Static page content verification failed: Copyright text string is missing or altered.");

        // Verify the Facebook link destination URL without hardcoding
        String fbUrl = homePage.getFacebookHref();
        Assert.assertTrue(fbUrl.contains("facebook.com/groups"), "Facebook community link is missing or broken.");
    }

    @Test(description = "Verify mouse hover interaction on the Facebook social icon")
    public void verifyFacebookHoverEffect() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        // [HOVER TEST] Execute advanced actions API mouse hover movement
        homePage.hoverOverFacebookIcon();

        // Assertion: Verify the link is still structurally sound and active after hover
        Assert.assertNotNull(homePage.getFacebookHref(), "Facebook element became detached after mouse interaction.");
    }
}