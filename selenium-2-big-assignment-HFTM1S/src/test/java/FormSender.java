import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormSender extends PageBase  {

    public FormSender(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.workaway.info/");
    }  
    public String sendIt () {
        try {
            Thread.sleep(1050);
            this.waitAndReturnElement(By.id("dropdown-account")).click();
            this.waitAndReturnElement(By.linkText("My settings")).click();
            WebElement bodyElemnet = this.waitAndReturnElement(By.tagName("body"));
            System.out.println(bodyElemnet.getText());
            Assert.assertTrue(bodyElemnet.getText().contains("Edit contact information"));
            this.waitAndReturnElement(By.xpath("//a[@href='/en/account/workawayer/settings/contact']")).click();
            Select dropdown = new Select(this.waitAndReturnElement(By.xpath("//select[@id='addressCountry']")));
            dropdown.selectByVisibleText("France");
            JavascriptExecutor js = (JavascriptExecutor) this.driver;
            js.executeScript("document.getElementById('city').setAttribute('value', 'budapest')");
            js.executeScript("document.getElementById('zip').setAttribute('value', '1084')");
            js.executeScript("document.getElementById('street').setAttribute('value', 'kuraly utca')");
            WebElement textArea = this.waitAndReturnElement(By.id("emergency_message"));
            js.executeScript("window.scrollBy(0,350)", "");
            textArea.sendKeys(Keys.TAB);
            textArea.clear();
            Map<String, Integer> rf = new RandomData().readFile();
            List<String> keysAsArray = new ArrayList<String>(rf.keySet());
            Random r = new Random();
            textArea.sendKeys(keysAsArray.get(r.nextInt(keysAsArray.size())));
            this.waitAndReturnElement(By.xpath("//button[contains(text(),'Update')]")).submit();
            
        } catch (InterruptedException | FileNotFoundException  ie) {
        }
        return (this.driver).getTitle();
    }
}