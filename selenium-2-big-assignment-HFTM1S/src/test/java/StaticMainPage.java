import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class StaticMainPage extends PageBase {

    public StaticMainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.workaway.info/");
    }

    public void checkTitlePage() {
        String expectedTitle = "Workaway.info the site for cultural exchange. Gap year volunteer for food and accommodation whilst travelling abroad.";
        String expectedUrl = "https://www.workaway.info/";
        this.driver.get(expectedUrl);
        try {
            Assert.assertEquals(expectedTitle, driver.getTitle());
            System.out.println("Navigated to correct webpage");
        } catch (Throwable pageNavigationError) {
            System.out.println("Didn't navigate to correct webpage");
        }
    }

    public void checkPageOpened() {
        String expectedUrl = "https://www.workaway.info/";
        this.driver.get(expectedUrl);
        try {
            Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
            System.out.println("Navigated to correct webpage");
        } catch (Throwable pageNavigationError) {
            System.out.println("Didn't navigate to correct webpage");
        }
    }
 
    public ResultPage search(String searchQuery) {
        this.waitAndReturnElement(By.xpath("//div[contains(@class, 'container nopadding-xs')]/div/ul[contains(@class, 'nav navbar-nav')]/li/a[@href='/en/hostlist']")).click();
        this.waitAndReturnElement(By.name("search")).sendKeys(searchQuery + "\n");
        this.waitAndReturnElement(By.xpath("//div[contains(@class, 'checkbox-custom checkbox-custom-inline')]"))
                .click();
        return new ResultPage(this.driver);
    }

    public String Historytest() {
        String expectedUrl = "https://www.workaway.info/";
        this.driver.get(expectedUrl);
        // Find element by link text and store in variable "Element"
        WebElement Element = driver.findElement(By.xpath("//a[@href='/en/hostlist?ht%5B%5D=hosttype_family']"));

        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        // This will scroll the page till the element is found
        js.executeScript("arguments[0].scrollIntoView();", Element);
        Element.click();
        js.executeScript("window.history.go(-1)");
        return (this.driver).getTitle();

    }

    public ResultPage hoverIt() {
        // locate the menu to hover over using its xpath
        WebElement ele = this.waitAndReturnElement(By.linkText("Giving back"));
        // Initiate mouse action using Actions class
        Actions actions = new Actions(this.driver);
        // move the mouse to the earlier identified menu option
        actions.moveToElement(ele).perform();
        this.waitAndReturnElement(By.xpath("//li[contains(@class, 'dropdown')]/ul/li/a[@href='/en/info/covid-19']"))
                .click();

        return new ResultPage(this.driver);
    }
}
