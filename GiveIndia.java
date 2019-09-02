package GiveIndia.Assesment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import junit.framework.Assert;

public class GiveIndia {

	public static void main(String[] args) throws IOException {
			System.setProperty("webdriver.chrome.driver","C:\\sw\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//1.Opening Page
			driver.get("https://en.wikipedia.org/wiki/Selenium");
			
			//2.External Link Section
			WebElement externalLinkSection=driver.findElement(By.xpath("//span[@class='toctext'][contains(text(),'External links')]"));
			externalLinkSection.click();
			List<String> hrefs = new ArrayList<String>();
			
		   try {

			   List<WebElement> externalLinks=driver.findElements(By.xpath("//div[@class='mw-parser-output']/ul[2]/li[a]"));
			   for ( WebElement anchor : externalLinks ) {
				    hrefs.add(anchor.findElement(By.tagName("a")).getAttribute("href"));
				}
				for ( String href : hrefs ) {
				    driver.get(href);
				    driver.navigate().back();
				}
		   } catch (Exception e) {
			e.printStackTrace();
		}
		   //3.click on Oxygen Link
		   WebDriverWait wait = new WebDriverWait(driver, 15);
		    WebElement oxygen = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='navbox']//tr[3]//td[7]//a[1]//span[1]")));
		   oxygen.click();
		   
		   //4.Verify featured article
		   if(driver.getCurrentUrl().equals("https://en.wikipedia.org/wiki/Oxygen")){
			   Assert.assertEquals("https://en.wikipedia.org/wiki/Oxygen",driver.getCurrentUrl() );
		   }
		   else{
			   System.out.println(driver.getCurrentUrl());
			   System.out.println("Invalid Page");
		   }
		   
		  // 5.Take Screenshot
		   WebElement properties=	driver.findElement(By.xpath("//table[@class='infobox']"));
	        TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile=new File("C:\\Users\\612466010\\Desktop\\Practice\\Image\\give.png");
            FileHandler.copy(SrcFile, DestFile);
            
            //6.Count pdf links
 		  List<WebElement>pdfLinks=	driver.findElements(By.xpath("//*[contains(text(),'(PDF)')]"));
 		   int count=pdfLinks.size();
 		   
 		   //7.Search
 		  WebElement search=driver.findElement(By.xpath("//input[@id='searchInput']"));
 		  search.sendKeys("pluto");
 		 WebDriverWait wait1 = new WebDriverWait(driver,30);
 		wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='suggestions-results']//a")));
 		List<WebElement> searchSuggestion= driver.findElements(By.xpath("//div[@class='suggestions-results']//a"));
 		for(int i = 0 ;i< searchSuggestion.size();i++)
		{
 			
			System.out.println(searchSuggestion.get(i).getText());
			
			if(searchSuggestion.get(i).getText().equals("Plutonium")&&i<=1)
			{
				break;
			}
		}
		   driver.close();



	}

}
