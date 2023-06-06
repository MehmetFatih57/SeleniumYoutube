package YouTubeProject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C02_Youtube {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrom.driver", "src/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://www.youtube.com");
        Thread.sleep(2000);

        WebElement girisYap = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[4]"));
        girisYap.click();

        WebElement email = driver.findElement(By.cssSelector("#identifierId"));
        email.sendKeys("mehmet.fatihh57@gmail.com", Keys.ENTER);
        Thread.sleep(2000);

        WebElement sonuc = driver.findElement(By.xpath("//div[@class='rQszV ']"));
        if (sonuc.isDisplayed()) {
            System.out.println("Giris Yapilamadi");
        } else {
            System.out.println("Giris Yapildi");
        }
            Thread.sleep(4000);
            driver.navigate().back();
            Thread.sleep(1000);
            driver.navigate().back();
            Thread.sleep(1000);
            driver.navigate().refresh();

            WebElement aramaKutusu = driver.findElement(By.xpath("//*[@aria-label='Search']"));
            aramaKutusu.click();
            Thread.sleep(2000);
            aramaKutusu.sendKeys("Simge Askin Olayim", Keys.ENTER);
            Thread.sleep(2000);

            WebElement sarki = driver.findElement(By.xpath("(//a[@id='video-title'])[1]"));
            sarki.click();
            Thread.sleep(20000);

            //WebElement buton = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[38]"));
            //buton.click();

            WebElement mix = driver.findElement(By.xpath("//span[@class='style-scope ytd-compact-radio-renderer']"));
            mix.click();
            Thread.sleep(2000);

            //WebElement like = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[11]"));
            //like.click();
        }
    }
