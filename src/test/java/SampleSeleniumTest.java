import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SampleSeleniumTest {
    public static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Before
    public void deleteCookies() {
        driver.manage().deleteAllCookies();

    }

    @Test
    public void chooseRegion(){
        driver.get("https://www.bestbuy.com/");
        WebElement region = driver.findElement(By.className("us-link"));
        region.click();
        String url = driver.getCurrentUrl();
        System.out.println(url);
        Assert.assertEquals(url, "https://www.bestbuy.com/?intl=nosplash");
    }

    @Test
    public void AddToCart(){
        driver.get("https://www.bestbuy.com/site/macbook-air-13-3-laptop-apple-m1-chip-8gb-memory-256gb-ssd-latest-model-gold/6418599.p?skuId=6418599");
        WebElement region = driver.findElement(By.className("us-link"));
        region.click();
        WebElement addToCartButton = driver.findElement(By.className("add-to-cart-button"));
        addToCartButton.click();
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("success")));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("added-to-cart")));

        WebElement addedToCart = driver.findElement(By.className("added-to-cart"));
        Assert.assertEquals(addedToCart.getText(), "Added to cart");
    }

    @Test
    public void Search(){
        driver.get("https://www.bestbuy.ca/en-ca");
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[4]/header/div/div/div[1]/div[2]/div[1]/div/div/form/div/input"));
        searchBar.sendKeys("Macbook");
        searchBar.sendKeys(Keys.ENTER);
        WebElement searchKey = driver.findElement(By.className("title_3A6Uh"));
        Assert.assertEquals(searchKey.getText(), "Results for: Macbook");
    }

    @Test
    public void ClosePopUp(){
        driver.get("https://www.bestbuy.com/");
        WebElement region = driver.findElement(By.className("us-link"));
        region.click();
        By by = By.cssSelector("#widgets-view-email-modal-mount > div > div > div:nth-child(1) > div > div > div > div > button");
        WebElement closeMark = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(by));
        closeMark.click();
        WebElement mark = driver.findElement(by);
        Assert.assertFalse(mark.isDisplayed());
    }


    @AfterClass
    public static void tearDown(){
        driver.close();
    }
}