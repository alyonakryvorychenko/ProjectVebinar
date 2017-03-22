import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by Alena on 03/20/2017.
 */
public class HomeWorkWeb1 {
    private WebDriver driver;
    private By inputLocator = By.id("sb_form_q");
    private By quantityItemSearchLocator = By.xpath("//ol[@id='b_results']/li[@class='b_algo']");
    private By firstItemSearchLocator = By.xpath("//ol[@id='b_results']/li[@class='b_algo'][1]");

    @BeforeClass
    public void setUp() {
       System.setProperty("webdriver.gecko.driver", "C:\\geckodriver-v0.10.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void main() {
        log("open the bing site");
        driver.navigate().to("https://www.bing.com/");

        //Type "automation" in "Search" field
        log("Enter 'automation in serach field'");

        WebElement inputSearch = driver.findElement(inputLocator);
        inputSearch.sendKeys("automation");
        inputSearch.submit();

        explicitWait(firstItemSearchLocator, "The item isn't displayed");
        String title = driver.getTitle();
        System.out.println("Site title: " + title);
        //find number of item in search
        int number = driver.findElements(quantityItemSearchLocator).size();
        System.out.println("Number of search items: " + number);
        //loop of memorizing each title of items in search results
        for (int i = 1; i <= number; i++) {
            String titleItemSearch = driver.findElement(By.xpath("//ol[@id='b_results']/li[@class='b_algo'][" + i + "]//h2/a")).getText();
            System.out.println("Found title(" + i + "): " + titleItemSearch);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private void log(String message) {
        Reporter.log(new Date().toString() + "\t" + message + "\n");
    }

    private void explicitWait(By locator, String textMessage) {
        log("Waiting appearing of element: " + locator.toString());
        Wait<WebDriver> wait = new WebDriverWait(driver, 20).withMessage(textMessage);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
