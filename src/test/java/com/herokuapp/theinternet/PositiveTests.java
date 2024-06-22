package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test
    public void loginTest(){
        //Variables
        String url = "https://the-internet.herokuapp.com/login";
        String username = "tomsmith";
        String password = "SuperSecretPassword!";

        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        String expectedSuccessMessage = "You logged into a secure area!";

        System.out.println("Test Started");

        //Create driver
        WebDriver driver = new ChromeDriver();
        System.out.println("Browser Started");

        //Open test page
        driver.get(url);

        //!maximize window
        driver.manage().window().maximize();
        System.out.println("Page is opened.");


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

        //Close Browser
        driver.quit();
        System.out.println("Test Finished");
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
