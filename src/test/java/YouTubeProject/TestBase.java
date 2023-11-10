package YouTubeProject;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class TestBase {
    /*
         TestBase class'indan obje olusturmanin onune gecmek icin bu class'i abstract yapabiliriz
         TestBase testBase new = TestBase(); yani bu sekilde obje olusturmanin onune gecmis oluruz
         Bu class'a extends yaptigimiz test class'larindan ulabiliriz
     */

    protected static WebDriver driver;
    protected ExtentReports extentReports; //-->Raporlamayı başlatmak için kullanılan class
    protected ExtentHtmlReporter extentHtmlReporter;//-->Raporu HTML formatında düzenler
    protected ExtentTest extentTest;//--> Test adınlarına eklemek istediğimiz bilgileri bu class ile oluştururuz

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(3000);
        //extentReports.flush();
        //driver.quit();
    }

    //HARD WAIT(Bekleme Methodu)
    public void canakkaleGecilmez(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Selenium Wait/Explicit Wait
    //visibilityOf(element) methodu
    public void visibleWait(WebElement element,int saniye){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(saniye));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    //visibilityOfElementLocated(locator) methodu
    public void visibleWait(By locator, int saniye){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(saniye));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //AlertWait methodu
    public void alertWait(int saniye){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(saniye));
        wait.until(ExpectedConditions.alertIsPresent());
    }

    //FluentWait visible Methodu
    public void visibleFluentWait(WebElement element,int saniye,int milisaniye){
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(saniye)).
                pollingEvery(Duration.ofMillis(milisaniye)).
                until(ExpectedConditions.visibilityOf(element));
    }

    //AcceptAlert
    public void acceptAlert(){
        driver.switchTo().alert().accept();
    }
    //DismissAlert
    public void dismissAlert(){
        driver.switchTo().alert().dismiss();
    }
    //getTextAlert
    public String getTextAlert(){
        return driver.switchTo().alert().getText();
    }
    //sendKeysAlert
    public void sendKeysAlert(String text){
        driver.switchTo().alert().sendKeys(text);
    }
    //DropDown VisibleText
    public void selectVisibleText(WebElement ddm, String text){
        Select select = new Select(ddm);
        select.selectByVisibleText(text);
    }
    //DropDown Index
    public void selectIndex(WebElement ddm,int index){
        Select select = new Select(ddm);
        select.selectByIndex(index);
    }
    //DropDown Value
    public void selectValue(WebElement ddm,String value){
        Select select = new Select(ddm);
        select.selectByValue(value);
    }
    //SwitchTo Window-1
    public void switchToWindow1(int index){
        List<String> pencereler = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(pencereler.get(index));
    }
    //SwitchTo Window-2
    public void switchToWindow2(int index){
        driver.switchTo().window(driver.getWindowHandles().toArray()[index].toString());
    }
    //Tum Sayfa Resmi(ScreenShot)
    public void screenShot(){
        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        String dosyaYolu = "src/test/java/techproed/TumSayfaResmi/screenShot" + tarih + ".jpeg";
        TakesScreenshot ts = (TakesScreenshot) driver;
        try {
            FileUtils.copyFile(ts.getScreenshotAs(OutputType.FILE),new File(dosyaYolu));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //WebElement Resmi (Webelement ScreenShot)
    public void webElementResmi(WebElement element){
        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        String dosyaYolu = "src/test/java/techproed/ElementResmi/WEscreenShot" + tarih + ".jpeg";
        try {
            FileUtils.copyFile(element.getScreenshotAs(OutputType.FILE),new File(dosyaYolu));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //UploadFile Robot Class
    public void uploadFilePath(String filePath) {
        try {
            canakkaleGecilmez(3);
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            canakkaleGecilmez(3);
            robot.keyPress(KeyEvent.VK_V);
            canakkaleGecilmez(3);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            canakkaleGecilmez(3);
            robot.keyRelease(KeyEvent.VK_V);
            canakkaleGecilmez(3);
            robot.keyPress(KeyEvent.VK_ENTER);
            canakkaleGecilmez(3);
            robot.keyRelease(KeyEvent.VK_ENTER);
            canakkaleGecilmez(3);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    //Extent Report Methodu
    public void extentReport(String browser,String reportName){
        extentReports = new ExtentReports();
        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyyy").format(new Date());
        String dosyaYolu = "testOutput/extentReports/extentReport"+tarih+".html";
        extentHtmlReporter = new ExtentHtmlReporter(dosyaYolu);
        extentReports.attachReporter(extentHtmlReporter);//-->HTML formatında raporlamayı başlatacak
        //Raporda gözükmesini isteğimiz bilgiler için
        extentReports.setSystemInfo("Browser",browser);
        extentReports.setSystemInfo("Tester","Mehmet Fatih");
        extentHtmlReporter.config().setDocumentTitle("Extent Report");
        extentHtmlReporter.config().setReportName(reportName);
    }
    //Click Method
    public void click(WebElement element){
        try {
            element.click();
        }catch (Exception e){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();" , element);
        }
    }
    //JS Scroll WE Method
    public void jsScrollWE(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    //JS Scroll END Method(Sayfanın altına)
    public void scrollEnd(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }
    //JS Scroll HOME Method(Sayfanın üstüne)
    public void scrollHome(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
    }
    //JS SendKeys() Method
    public void jsSendKeys(String text,WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='"+text+"'",element);
    }
    //JS setAttribute() Method
    public void jsSetAttribute(String attribute,String text,WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('"+attribute+"','"+text+"')",element);
    }
}