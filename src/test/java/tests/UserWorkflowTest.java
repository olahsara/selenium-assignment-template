package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import pages.ProfileEditModal;
import utils.ConfigReader;


public class UserWorkflowTest extends BaseTest {
    
    private HomePage homePage;
    private ProfileEditModal profileModal;

    @Test(description = "Step 1: Verify successful login")
    public void loginTest() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePage();
        Assert.assertTrue(driver.getTitle().contains("Subtitle Ninjas"));
        
        // Login with valid credentials
        LoginPage loginPage = homePage.header.clickLogin();
        loginPage.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        
        // Verify that we are logged in
        Assert.assertTrue(homePage.header.isUserLoggedIn(), "User should be logged in but is not.");
    }

    // This test depends on the successful login test, so it will only run if loginTest() passes
    @Test(dependsOnMethods = {"loginTest"}, description = "Step 2: Verify updating profile bio form as a logged-in user")
    public void updateProfileTest() {
        profileModal = homePage.header.goToProfileEdit();
        
        // Update the profile bio with new text
        profileModal.updateProfileInformation(ConfigReader.getProperty("bio"));

        // Verify that the profile update was successful
        Assert.assertEquals(profileModal.getBioText(),ConfigReader.getProperty("bio"),
                "Profile sync failed: Live biography text does not match external properties value.");
    }

    // This test depends on the successful profile update test, so it will only run if updateProfileTest() passes
    @Test(dependsOnMethods = {"updateProfileTest"}, description = "Step 3: Verify successful logout")
    public void logoutTest() {
        // Log out action
        homePage.header.logout();

        // Assertion: Confirm user indicators are wiped completely from the current context
        Assert.assertFalse(homePage.header.isUserLoggedIn(), "Session drop failed: User authentication state flag remained active.");
    }
}