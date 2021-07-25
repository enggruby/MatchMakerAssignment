import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class ShaadiTest {
	public AppiumDriver driver=null;
	public String username=""; 
	public String pwd="";
	
	final By loginlink=MobileBy.id("btn_morph_login");		
	final By emailID=MobileBy.id("edt_username");
	final By password=MobileBy.id("edt_password");
	final By loginbtn=MobileBy.id("btn_login");
	//final By img_cancel=MobileBy.id("img_cancel");
	final By shaadihome=MobileBy.id("tvMyShaadi");	
	final static By premiumwidgettext =By.xpath("//*[contains(@text,'Premium Matches')]");
	final static By premiumconnect=By.xpath("//*[@resource-id='com.shaadi.android:id/recyclerView']//android.widget.LinearLayout[@resource-id='com.shaadi.android:id/cl_content']//androidx.recyclerview.widget.RecyclerView[@resource-id='com.shaadi.android:id/recycler_view']//android.view.ViewGroup[@index=0]//android.view.ViewGroup[@index=0]//android.widget.FrameLayout[@resource-id='com.shaadi.android:id/ctaView']//android.widget.TextView[@text='Connect Now']");
	final static By premiumupgradeconnect=By.xpath("//*[@resource-id='com.shaadi.android:id/recyclerView']//android.widget.LinearLayout[@resource-id='com.shaadi.android:id/cl_content']//androidx.recyclerview.widget.RecyclerView[@resource-id='com.shaadi.android:id/recycler_view']//android.view.ViewGroup[@index=0]//android.view.ViewGroup[@index=0]//android.widget.FrameLayout[@resource-id='com.shaadi.android:id/ctaView']//android.widget.TextView[@text='Connect Now']");
	final static By newmatchconnect=By.xpath("//*[@resource-id='com.shaadi.android:id/recyclerView']//android.widget.LinearLayout[@resource-id='com.shaadi.android:id/cl_content']//androidx.recyclerview.widget.RecyclerView[@resource-id='com.shaadi.android:id/recycler_view']//android.view.ViewGroup[@index=0]//android.view.ViewGroup[@index=0]//android.widget.FrameLayout[@resource-id='com.shaadi.android:id/ctaView']//android.widget.TextView[@text='Connect Now']");
	final static By newmatchtext =By.xpath("//*[contains(@text,'New Matches')]");
	
	
/* This method is used to setup capabilities and launch Appium driver*/	
	@BeforeTest
	public void driversetup()
	{
	System.out.println("launching driver");
	DesiredCapabilities caps= new DesiredCapabilities ();		
	caps.setCapability("PlatfromName","Android");
	caps.setCapability("deviceName", "Pixel_2_API_30");
	caps.setCapability("automationName", "UiAutomator2");		
	String appurl=System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"com.shaadi.android_4418_apps.evozi.com.apk";
	caps.setCapability("app", appurl);
	caps.setCapability("udid", "emulator-5554");
	try {
		URL url= new URL("http://0.0.0.0:4723/wd/hub");
		 driver =new AndroidDriver(url,caps);	
	}
	catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
/* This method call multiple method to login, Go back to Home screen, verify premium & New match widget
 *  and send Premium connect & Newmatch connect*/
	@Test
	public void fn_assignment()
	{
		fnlogin(username,pwd); //Login into app, update username and pwd in class variables
		verifypremiumwidget(); // Verify Premium Widget
		verifynewmatchwidget(); // Verify New match Widget
		sendpremiumconnect(); // Send Request to Premium connect
		sendpnewmatchconnect(); // Send Request to new match connect
	} 
	
	
	/* This method is used to close the app*/
	@AfterTest
	public void terminateBrowser(){
	driver.closeApp();
	}
	
	
	
	/* This method is used to login into Shaadi App*/
	 void fnlogin(String username, String pwd) 
	{	
		 try
		 {
			System.out.println(driver.getSessionId());		
			waitForElementToBeVisible(loginlink);
			driver.findElement(loginlink).click();
			waitForElementToBeVisible(emailID);
			driver.findElement(emailID).sendKeys(username);
			driver.findElement(password).sendKeys(pwd);
			driver.findElement(loginbtn).click();
			//Thread.sleep(5000);
			waitFor("5");
			waitForElementToBeVisible(shaadihome);	
			driver.findElement(shaadihome).click();
			waitFor("5");
		 }
		 catch(NoSuchElementException e)
		 {
			 e.printStackTrace();
		 }
	}
	 
	 /* This method is used to scroll on mobile screen until passed element is found */
	  void scrolltillelementvisible(int elewait,By by) throws InterruptedException
		{
			int i=0;
			WebDriverWait	 wait=new WebDriverWait(driver,elewait);
			while(i<10)
			{
				try
				{
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
					System.out.println("element Found");
					break;
				}
				catch(Exception e) 
				{
					scrolldown();
					i++;
					waitFor("1");
					System.out.println("Scrolling with i:"+i);
				}
			}
		}
	  
	  /* This method is used to scroll on mobile screen */
	  void scrolldown() throws InterruptedException
		{
			Dimension dim=driver.manage().window().getSize();
			int x=driver.manage().window().getSize().width/2;
			int scrollstart=(int) (dim.getHeight()*.3);
			int scrollend=(int) (dim.getHeight()*.1);
			new TouchAction((PerformsTouchActions) driver).press(PointOption.point(scrollstart,scrollend)).moveTo(PointOption.point(scrollend,0)).release().perform();
			System.out.println("Scrolling completed");			
		 	waitFor("2");
			
		}
	  /* This method is used to wait for an element on mobile screen */
	   public void waitForElementToBeVisible(By locatorObject) {
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(locatorObject));
		}
	
	   public void waitFor(String timeInSec) {
		   try {
			Thread.sleep(Integer.parseInt(timeInSec) * 1000);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	   
	   /* This method is used verify premium widget */
	   void verifypremiumwidget() 
		{
		   try {
			
			 String s=driver.findElement(premiumwidgettext).getText(); // capturing the text Premium Matches
			 assertTrue(s.contains("Premium Matches"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
	  
	   /* This method is used verify new matches widget */
	   void verifynewmatchwidget() 
		{
		   try {
			 scrolltillelementvisible(5,newmatchtext);
			 String s=driver.findElement(newmatchtext).getText();
			 assertTrue(s.contains("New Matches"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
	   
	   /* This method is used to send request to  premium connect */
	   void sendpremiumconnect() 
		{
		   try {
			scrolltillelementvisible(5,premiumconnect);
			//driver.findElement(premiumconnect).click();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
	   
	   /* This method is used to send request to  new matches connect */
	   void sendpnewmatchconnect() 
	 		{
	 		   try {
	 			scrolltillelementvisible(5,newmatchconnect);
	 			//driver.findElement(newmatchconnect).click();
	 			
	 		} catch (InterruptedException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();}
	 		}
	  
}
