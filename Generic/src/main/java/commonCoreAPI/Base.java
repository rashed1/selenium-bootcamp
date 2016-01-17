package commonCoreAPI;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rashed on 1/17/2016.
 */
public class Base {

    public WebDriver driver = null;
    public static Logger logger = Logger.getLogger(Base.class);

    @Parameters({"useCloudEnv","userName","key","os","browser","browserVersion","url"})
    @BeforeMethod
    public void setUp(@Optional("false")Boolean useCloudEnv, @Optional("rashed1") String userName, @Optional("2601a7b6-4b39-4712-992a-59ddcd85694c")
    String key, @Optional("Windows 10")String OS, @Optional("firefox") String browser,
                      @Optional("43.0.1") String browserVersion,
                      @Optional("https://www.aetna.com") String url)throws IOException {

        if(useCloudEnv==true){
            //run on cloud
            logger.setLevel(Level.INFO);
            logger.info("Test is running on cloud env");
            getCloudDriver(userName,key,OS,browser,browserVersion);
            System.out.println("Tests is running on Saucelabs, please wait for result");

        }else{
            //run on local
            getLocalDriver(OS,browser,browserVersion);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    public WebDriver getCloudDriver(String userName,String key,
                                    String OS,String browser,String browserVersion)throws IOException{
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platform", OS);
        cap.setBrowserName(browser);
        cap.setCapability("version",browserVersion);
        this.driver = new RemoteWebDriver(new URL("http://"+userName+":"+key+"@ondemand.saucelabs.com:80/wd/hub"), cap);

        return driver;
    }
    public WebDriver getLocalDriver(String OS, String browser, String browserVersion){
        if(browser.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }else if(browser.equalsIgnoreCase("chrome")){
            if(OS.equalsIgnoreCase("mac")){
                System.setProperty("webdriver.chrome.driver","Generic/selenium-browser-driver/chromedriver");driver = new ChromeDriver();
            }else if(OS.equalsIgnoreCase("windows")){
                System.setProperty("webdriver.chrome.driver","Generic/selenium-browser-driver/chromedriver.exe");
                driver = new ChromeDriver();
            }
        }else if(browser.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver","Generic/selenium-browser-driver/IEDriverServer.exe");
            driver = new ChromeDriver();
            driver = new InternetExplorerDriver();
        }else if(browser.equalsIgnoreCase("safari")){
            driver = new SafariDriver();
        }

        return driver;
    }

    @AfterMethod
    public void cleanUp() {
        driver.close();
    }

    //Helper methods

    //using different locator
    // find by Id
    public void clickById(String locator) {
        driver.findElement(By.id(locator)).click();
    }

    //find by name
    public void clickByName(String locator) {
        driver.findElement(By.id(locator)).click();
    }

    //find by by css
    public void clickOnCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }

    //find by Xpath
    public void typeByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    //find text using different locators
    //find text by css
    public String getTextByCss(String locator) {
        String st = driver.findElement(By.cssSelector(locator)).getText();
        return st;
    }

    // find text by Xpath
    public String getTextByXpath(String locator) {
        String st = driver.findElement(By.xpath(locator)).getText();
        return st;
    }

    //find text by Id
    public String getTextById(String locator) {
        return driver.findElement(By.id(locator)).getText();
    }

    //find text by Name
    public String getTextByName(String locator) {
        String st = driver.findElement(By.name(locator)).getText();
        return st;
    }
    //helper methods to send ENTER key
    public void keysInput(String locator) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }


    //find by css and input a value
    public void typeByCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.CLEAR);
    }

    //find by css, remove previous input, input new value and enter
    public void typeByCssThenEnter(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.CLEAR, value, Keys.ENTER);
    }

    //Finding web Element(s)

    //find a single web element by css
    public WebElement getWebElementByCss(String locator) {
        return driver.findElement(By.cssSelector(locator));
    }

    // find a single web element by xpath
    public WebElement getWebElementByXpath(String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element;
    }
    // find web elements, i.e., a list by css
    public List<WebElement> getWebElementsByCss(String locator) {
        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList = driver.findElements(By.cssSelector(locator));
        return elementList;
    }


    //constant sleep time to help with latency
    public void sleepFor(int sec) throws InterruptedException {
        Thread.sleep(1000 * sec);
    }

    //Navigation

    //navigate to next page
    public void navigateBack() {
        driver.navigate().back();}

    //navigate to previous page
    public void navigateForward() {
        driver.navigate().forward();}

    //Synchronization
    public void waitUntilClickAble(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitUntilSelectable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }
    //get WebElements
    public List<WebElement> getWebElements(String locator){
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));
        return elements;
    }
    //get texts
    public List<String> getElementTexts(String  locator){
        List<String> texts = new ArrayList<String>();
        List<WebElement> element = new ArrayList<WebElement>();
        element = getWebElements(locator);
        for(WebElement st:element){
            texts.add(st.getText());
        }
        return texts;
    }

    //Keys
    public void clearInputBox(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }

    public void mouseHoverByCSS(String locator){
        try {
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }
    public void mouseHoverByXpath(String locator){
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }

    }
    //handling Alert
    public void okAlert(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert(){
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    //iFrame Handle
    public void iframeHandle(WebElement element){
        driver.switchTo().frame(element);
    }

    public void goBackToHomeWindows(){
        driver.switchTo().defaultContent();
    }

    //get Links
    public void getLinks(String locator){
        driver.findElement(By.linkText(locator)).findElement(By.tagName("a")).getText();
    }

    //Taking Screen shots
    public void takeScreenShot()throws IOException {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("screenShots.png"));
    }



}
