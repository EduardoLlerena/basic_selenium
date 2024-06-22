package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.Optional;

public class LoginTests {
    private WebDriver driver;
    //always run necessary because before and after method are not in a group, and positive tests are defined in the xml as groups only
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser){
        String url = "https://the-internet.herokuapp.com/login";
        //Create driver
        switch(browser){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.get(url);


        System.out.println("Browser Started");

        //!maximize window
        driver.manage().window().maximize();
        System.out.println("Page is opened.");

    }

    @AfterMethod(alwaysRun = true)
    private void closeDriver(){
        driver.quit();
    }

    @Test (priority = 1,groups={"positiveTests","smokeTests"})
    public void positiveLoginTest(){
        //Variables
        String username = "tomsmith";
        String password = "SuperSecretPassword!";

        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        String expectedSuccessMessage = "You logged into a secure area!";

        System.out.println("Test Started");


        //Enter Username
        WebElement usernameWebElement = driver.findElement(By.id("username"));
        usernameWebElement.sendKeys(username);

        //Enter Password
        WebElement passswordWebElement = driver.findElement(By.name("password"));
        passswordWebElement.sendKeys(password);

        //Click Login Button
        WebElement loginButtonWebElement = driver.findElement(By.tagName("button"));
        loginButtonWebElement.click();

        sleep(3);

        //Successful login message
        //WebElement successMessageWebElement = driver.findElement(By.cssSelector("div#flash.flash.success"));
        //WebElement successMessageWebElement = driver.findElement(By.className("success"));
        WebElement successMessageWebElement = driver.findElement(By.xpath("//div[@id='flash']"));

        //Logout Button
        WebElement logoutButtonWebElement = driver.findElement(By.xpath("//a[@class='button secondary radius']"));

        //Assertions
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl,expectedUrl,"Actual page url is no the same as expected");
        Assert.assertTrue(logoutButtonWebElement.isDisplayed(),"Logout button is not visible.");
        Assert.assertTrue(successMessageWebElement.getText().contains(expectedSuccessMessage),"Success message does not contain the one expected. \n Actual message:" +successMessageWebElement.getText() + "\n Expected Message:" + expectedSuccessMessage);

        System.out.println("Test Finished");
    }

    @Parameters({"username","password","expectedMessage"})
    @Test (priority = 2,groups={"negativeTests","smokeTests"})
    public void negativeLoginTests(String username, String password, String expectedMessage){
        WebElement usernameWebElement = driver.findElement(By.id("username"));
        usernameWebElement.sendKeys(username);

        WebElement passswordWebElement = driver.findElement(By.name("password"));
        passswordWebElement.sendKeys(password);

        WebElement loginButtonWebElement = driver.findElement(By.tagName("button"));
        loginButtonWebElement.click();

        WebElement incorrectUsernameMessage = driver.findElement(By.xpath("//div[@id='flash']"));

        //Assertions
        Assert.assertTrue(incorrectUsernameMessage.getText().contains(expectedMessage ),"Expected incorrect message is not contained in the displayed message.");
    }
    /**
     * Stop for the given ammount of seconds
     * @param seconds
     */
    //! Sleeps in the case we want to see what's happening... explicit waits not recommended
    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
