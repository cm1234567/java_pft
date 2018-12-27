package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
   private final Properties properties;
   WebDriver driver;

   private SessionHelper sessionHelper;
   private NavigationHelper navigationHelper;
   private GroupHelper groupHelper;
   private ContactHelper contactHelper;
   private boolean acceptNextAlert = true;
   private StringBuffer verificationErrors = new StringBuffer();
   private String browser;

   public ApplicationManager(String browser)  {
      this.browser = browser;
      properties = new Properties();
   }

   public void init() throws IOException {
      String target = System.getProperty("target", "local");
      properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

      if (browser.equals(BrowserType.FIREFOX)) {
         driver = new FirefoxDriver();
      } else if (browser.equals(BrowserType.CHROME)) {
         driver = new ChromeDriver();
      } else if (browser.equals(BrowserType.EDGE)) {
         driver = new EdgeDriver();
      } else if (browser.equals(BrowserType.IE)) {
         driver = new InternetExplorerDriver();
      }

      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      driver.get(properties.getProperty("web.baseUrl"));
      groupHelper = new GroupHelper(driver);
      navigationHelper = new NavigationHelper(driver);
      contactHelper = new ContactHelper(driver);
      sessionHelper = new SessionHelper(driver);
      sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
   }

    public void stop() {
      driver.quit();
      String verificationErrorString = verificationErrors.toString();
      if (!"".equals(verificationErrorString)) {
         fail(verificationErrorString);
      }
   }

   public boolean isElementPresent(By by) {
      try {
         driver.findElement(by);
         return true;
      } catch (NoSuchElementException e) {
         return false;
      }
   }

   public String closeAlertAndGetItsText() {
      try {
         Alert alert = driver.switchTo().alert();
         String alertText = alert.getText();
         if (acceptNextAlert) {
            alert.accept();
         } else {
            alert.dismiss();
         }
         return alertText;
      } finally {
         acceptNextAlert = true;
      }
   }

   public GroupHelper group() {
      return groupHelper;
   }

   public NavigationHelper goTo() {
      return navigationHelper;
   }

   public ContactHelper contact() {
      return contactHelper;
   }

}
