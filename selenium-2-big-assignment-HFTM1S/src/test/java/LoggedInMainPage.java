
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.By;
import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.Robot;

class LoggedInMainPage extends PageBase {

    public LoggedInMainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.est.org.tn/");
    }

    public String login() {
        this.waitAndReturnElement(By.xpath(
                "//nav[@id='navbar-user']/ul[@id='navbar-user-nav']/li[contains(@class, 'dropdown')]/a[contains(@class, 'dropdown-toggle')]/div[contains(@class, 'navbar-user-item')]"))
                .click();
        this.waitAndReturnElement(By.linkText("LOGIN")).click();
        User u = new User();
        u.updateUserData();
        System.out.println(u.getuserName());
        this.waitAndReturnElement(By.name("un")).sendKeys(u.getuserName());
        try {
            Thread.sleep(1150);
            this.waitAndReturnElement(By.name("pw")).sendKeys(u.getpassword() + "\n");
        } catch (InterruptedException ie) {
        }
        cookieRead cr = new cookieRead(this.driver);
        cr.read();
        return (this.driver).getTitle();
    }

    public String logOut() {
        this.login();
        try {
            Thread.sleep(1000);
            this.waitAndReturnElement(By.xpath(
                    "//nav[contains(@class, 'navbar navbar-user-loggedin')]/ul[@id='navbar-user-nav']/li[@id='dropdown-account']/a/div[contains(@class,'navbar-user-item')]"))
                    .click();
            this.waitAndReturnElement(By.linkText("Logout")).click();
        } catch (InterruptedException ie) {
        }
        return (this.driver).getTitle();

    }

    public ResultPage readCo() {
        try {

            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    Cookie ck = new Cookie(name, value);
                    System.out.println(ck);
                    driver.manage().addCookie(ck); // This will add the stored cookie to your current session
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        driver.get("https://www.workaway.info/en/account/workawayer");
        return new ResultPage(this.driver);
    }

    // Uploading the file using sendKeys
    public ResultPage uploadPicture() {
        try {
            this.login();

            Thread.sleep(1000);
            this.driver.findElement(By.xpath(
                    "//nav[contains(@class, 'navbar navbar-user-loggedin')]/ul[@id='navbar-user-nav']/li[@id='dropdown-account']/a/div[contains(@class,'navbar-user-item')]"))
                    .click();
            this.driver.findElement(By.linkText("My profile photos")).click();
            // link text locator for uploading a photo..
            Thread.sleep(1000);
            String xpathExpression = "//form[@id='fileupload']/div[contains(@class, 'row fileupload-buttonbar')]/div[contains(@class, 'col-lg-12')]/span[contains(@class, 'btn btn-success fileinput-button')]";
            WebElement addFile = this.driver.findElement(By.xpath(xpathExpression));
            // click on ‘Choose file’ to upload the desired file
            addFile.click(); // Click on browse option on the webpage
            // copying File path to Clipboard
            StringSelection str = new StringSelection(System.getProperty("user.dir") + "\\src\\otherfiles\\lmd.jpg");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            Robot rb = new Robot();
            rb.delay(300);

            // press Contol+V for pasting
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);

            // release Contol+V for pasting
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);
            rb.delay(200);

            // for pressing and releasing Enter
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);

            this.waitAndReturnElement(By.xpath("//button[@type='submit']")).click();

        } catch (InterruptedException | AWTException ie) {
        }

        return new ResultPage(this.driver);
    }

}
