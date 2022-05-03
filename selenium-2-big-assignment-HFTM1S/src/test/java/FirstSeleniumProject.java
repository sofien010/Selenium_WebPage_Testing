import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class FirstSeleniumProject {
    public WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setup() {
        // initializing driver variable using ChromeDriver
        driver = new ChromeDriver();
        // configurate the webdriver
        WebDriverManager.chromedriver().config().setProperties("src\\otherfiles\\webdrivermanager.properties");
        // Maximizes the browser window
        driver.manage().window().maximize();
        // define the periode for waiting
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    /* Login */
    public void login() {
        LoggedInMainPage mainPage = new LoggedInMainPage(this.driver);
        String expectedProfile = mainPage.login();
        ReadConfigFile rd = new ReadConfigFile();
        Assert.assertEquals(rd.loadProperties().getProperty("profile"), expectedProfile);
    }

    @Test
    /* LogOut */
    public void LogOut() {
        LoggedInMainPage mainPage = new LoggedInMainPage(this.driver);
        String expectedMainPage = mainPage.logOut();
        ReadConfigFile rd = new ReadConfigFile();
        Assert.assertEquals(rd.loadProperties().getProperty("main_page"), expectedMainPage);
    }

    @Test
    /* LogOut with Cookies */
    public void LoginWithCookies() {
        LoggedInMainPage mainPage = new LoggedInMainPage(this.driver);
        ResultPage resultPage = mainPage.readCo();
        Assert.assertTrue(resultPage.getBodyText().contains("Welcome !"));

    }

    @Test
    /* Form sending with user */
    public void sendFormWithUser() {
        LoggedInMainPage mainPage = new LoggedInMainPage(this.driver);
        FormSender sender = new FormSender(this.driver);
        mainPage.login();
        String expectedMainPage = sender.sendIt();
        ReadConfigFile rd = new ReadConfigFile();
        Assert.assertEquals(rd.loadProperties().getProperty("profile"), expectedMainPage);
    }

    @Test
    /* Check Hover Button */
    public void hoverButton() {
        StaticMainPage wAway = new StaticMainPage(this.driver);
        ResultPage resultPage = wAway.hoverIt();
        Assert.assertTrue(resultPage.getBodyText().contains("COVID-19"));

    }

    @Test
    /* Static Page test */
    public void searchForTheQueries() {
        String[] searchQueries = { "Hiking", "Children" };
        for (String searchQuery : searchQueries) {
            StaticMainPage mainPage = new StaticMainPage(this.driver);
            ResultPage searchResultPage = mainPage.search(searchQuery);
            String bodyText = searchResultPage.getBodyText();
            Assert.assertFalse(bodyText.contains("No results were found for this search."));
        }
    }

    @Test
    /* Back Button Click */
    public void backHistory() {
        StaticMainPage wAway = new StaticMainPage(this.driver);
        String expectedMainPage = wAway.Historytest();
        ReadConfigFile rd = new ReadConfigFile();
        Assert.assertEquals(rd.loadProperties().getProperty("main_page"), expectedMainPage);
    }

    @Test
    /* Check If Page Opened Or Not */
    public void OpenPage() {
        StaticMainPage wAway = new StaticMainPage(this.driver);
        wAway.checkPageOpened();
    }

    @Test
    /* Read The Page Title */
    public void readTitlePage() {
        StaticMainPage wAway = new StaticMainPage(this.driver);
        wAway.checkTitlePage();
    }

    @Test
    /* Upload Picture */
    public void uploadPicture() {
        LoggedInMainPage mainPage = new LoggedInMainPage(this.driver);
        ResultPage resultOfDownload = mainPage.uploadPicture();
        String bodyText = resultOfDownload.getBodyText();
        ReadConfigFile rd = new ReadConfigFile();
        Assert.assertFalse(bodyText.contains(rd.loadProperties().getProperty("name_image_upload")));
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
