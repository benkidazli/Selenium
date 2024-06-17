import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Tests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("file:///C:/Users/lizzy/Downloads/qa-test%20.html");
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    @Order(1)
    public void testSuccessfulLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("authPage")), "Login failed");
    }

    @Test
    @Order(2)
    public void testInvalidEmailLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("wrong@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("invalidEmailPassword")), "Error message not displayed");
    }

    @Test
    @Order(3)
    public void testInvalidPasswordLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("wrong");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("invalidEmailPassword")), "Error message not displayed");
    }

    @Test
    @Order(4)
    public void testInvalidEmailFormatLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("testprotei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("emailFormatError")), "Error message not displayed");
    }

    @Test
    @Order(5)
    public void testEmptyEmailLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("emailFormatError")), "Error message not displayed");
    }

    @Test
    @Order(6)
    public void testEmptyPasswordLogin() {
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("");
        driver.findElement(By.id("authButton")).click();
        Assertions.assertTrue(isElementPresent(By.id("invalidEmailPassword")), "Error message not displayed");
    }

    @Test
    @Order(7)
    public void testSuccessfulDataEntry() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("valid@example.com");
        driver.findElement(By.id("dataName")).sendKeys("Иван");
        driver.findElement(By.id("dataGender")).sendKeys("Мужской");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(driver.findElement(By.className("uk-modal-dialog")).isDisplayed(), "Success alert not displayed");
    }

    @Test
    @Order(8)
    public void testInvalidEmailFormatDataEntry() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("invalid-email");
        driver.findElement(By.id("dataName")).sendKeys("Иван");
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(isElementPresent(By.id("emailFormatError")), "Error message not displayed");
    }

    @Test
    @Order(9)
    public void testEmptyNameDataEntry() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("valid@example.com");
        driver.findElement(By.id("dataName")).sendKeys("");
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(isElementPresent(By.id("blankNameError")), "Error message not displayed");
    }

    @Test
    @Order(10)
    public void testOptionalFields() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("valid@example.com");
        driver.findElement(By.id("dataName")).sendKeys("Иван");
        driver.findElement(By.id("dataGender")).sendKeys("Мужской");
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(driver.findElement(By.className("uk-modal-dialog")).isDisplayed(), "Success alert not displayed");

        WebElement table = driver.findElement(By.id("dataTable"));
        Assertions.assertTrue(table.getText().contains("valid@example.com"), "Email not found in table");
        Assertions.assertTrue(table.getText().contains("Иван"), "Name not found in table");
        Assertions.assertTrue(table.getText().contains("Мужской"), "Gender not found in table");
        Assertions.assertTrue(table.getText().contains("Нет"), "Choice 1 not found in table");
    }

    @Test
    @Order(11)
    public void testMultipleChoices() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("valid@example.com");
        driver.findElement(By.id("dataName")).sendKeys("Иван");
        driver.findElement(By.id("dataGender")).sendKeys("Мужской");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataCheck12")).click();
        driver.findElement(By.id("dataSelect22")).click();
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(driver.findElement(By.className("uk-modal-dialog")).isDisplayed(), "Success alert not displayed");

        WebElement table = driver.findElement(By.id("dataTable"));
        Assertions.assertTrue(table.getText().contains("valid@example.com"), "Email not found in table");
        Assertions.assertTrue(table.getText().contains("Иван"), "Name not found in table");
        Assertions.assertTrue(table.getText().contains("Мужской"), "Gender not found in table");
        Assertions.assertTrue(table.getText().contains("1.1, 1.2"), "Choice 1 not found in table");
        Assertions.assertTrue(table.getText().contains("2.2"), "Choice 2 not found in table");
    }

    @Test
    @Order(12)
    public void testEmptyEmailDataEntry() {
        testSuccessfulLogin();
        driver.findElement(By.id("dataEmail")).sendKeys("");
        driver.findElement(By.id("dataName")).sendKeys("Иван");
        driver.findElement(By.id("dataGender")).sendKeys("Мужской");
        driver.findElement(By.id("dataSend")).click();
        Assertions.assertTrue(isElementPresent(By.id("emailFormatError")), "Error message not displayed");
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
