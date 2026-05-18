package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utils.ConfigReader;


public class BaseTest {
    protected WebDriver driver;
    protected final String BASE_URL = ConfigReader.getProperty("url");

    @BeforeClass(description = "Initialize a standalone browser lifecycle container")
    public void setUp() {
        // webdriver_config and headless_execution configuration
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");

        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
        }
        
        driver = new ChromeDriver(options);
    }

    @AfterClass(description = "Safely terminate and dispose of the browser runtime context")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}