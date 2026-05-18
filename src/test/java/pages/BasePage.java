package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.function.Function;

import utils.ConfigReader;


public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigReader.getProperty("explicit_wait"))));
    }

    protected WebElement waitForVisibility(By locator) {
        killAllPopupsAndOverlays();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForPresence(By locator) {
        killAllPopupsAndOverlays();
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        killAllPopupsAndOverlays();
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForCustomCondition(Function<WebDriver, Boolean> condition) {
        wait.until(condition);
    }

    /**
     * Helper method to click on an element specified by the locator
     * @param locator the locator of the element to click
     */
    protected void click(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (Exception e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Helper method to write text into an input field specified by the locator
     * @param locator the locator of the input field
     * @param text the text to write into the input field
     */ 
    protected void writeText(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Helper method to get text from an element specified by the locator
     * @param locator the locator of the element
     * @return the text of the element
     */
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * INJECTOR: Injects an overriding CSS stylesheet into the browser runtime.
     * Prevents async anti-adblock scripts and Google Cookie Consent modals from rendering,
     * while forcefully restoring normal body scrolling and link pointer-events.
     */
    private void killAllPopupsAndOverlays() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    // Inject global override stylesheet into the head if not already present
                    "if (!document.getElementById('selenium-overlay-killer')) {" +
                            "   var style = document.createElement('style');" +
                            "   style.id = 'selenium-overlay-killer';" +
                            "   style.innerHTML = '#adblock-overlay, .fc-consent-root, .fc-dialog-overlay { display: none !important; visibility: hidden !important; pointer-events: none !important; } " +
                            "                      body, html { overflow: auto !important; position: static !important; height: auto !important; }';" +
                            "   document.head.appendChild(style);" +
                            "}" +
                            // As an extra precaution, physically remove the elements from the DOM if already loaded
                            "var ab = document.getElementById('adblock-overlay'); if(ab) ab.remove();" +
                            "var fc = document.querySelector('.fc-consent-root'); if(fc) fc.remove();" +
                            "document.body.classList.remove('adblock-active');"
            );
        } catch (Exception e) {
            // Silently ignore if JS execution fails during an uncompleted page transition
        }
    }
}