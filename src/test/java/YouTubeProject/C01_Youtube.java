package YouTubeProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class C01_Youtube {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrom.driver" , "src/resources/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.get("https://www.youtube.com");

        WebElement youtubeAnasayfa = driver.findElement(By.xpath("(//a[@class='yt-simple-endpoint style-scope ytd-guide-entry-renderer'])[1]"));
        System.out.println(youtubeAnasayfa.getText());

        WebElement ilkVideo = driver.findElement(By.xpath("(//a[@class='yt-simple-endpoint focus-on-expand style-scope ytd-rich-grid-media'])[2]"));
        ilkVideo.click();

    }
}
