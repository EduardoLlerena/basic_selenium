package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
    @Test (priority=1,enabled = true,groups={"negativeTests","smokeTests"})
    public void incorrectUsername(){
        String url = "https://the-internet.herokuapp.com/login";
        String incorrectUsername = "tomsmiths";
        String password = "SuperSecretPassword!";
        String expectedIncorrectMessage = "Your username is invalid!";

        WebDriver driver = new FirefoxDriver();

        driver.get(url);

        WebElement usernameWebElement = driver.findElement(By.id("username"));
        usernameWebElement.sendKeys(incorrectUsername);

        WebElement passswordWebElement = driver.findElement(By.name("password"));
        passswordWebElement.sendKeys(password);

        WebElement loginButtonWebElement = driver.findElement(By.tagName("button"));
        loginButtonWebElement.click();

        WebElement incorrectUsernameMessage = driver.findElement(By.xpath("//div[@id='flash']"));

        //Assertions
        Assert.assertTrue(incorrectUsernameMessage.getText().contains(expectedIncorrectMessage ),"Expected incorrect message is not contained in the displayed message.");

        driver.quit();
    }

    @Test (priority=2,enabled = true,groups={"negativeTests"})
    public void incorrectPassword(){
        String url = "https://the-internet.herokuapp.com/login";
        String username = "tomsmith";
        String incorrectPassword = "SuperSecretPassword";
        String expectedIncorrectMessage = "Your password is invalid!";

        WebDriver driver = new ChromeDriver();

        driver.get(url);

        WebElement usernameWebElement = driver.findElement(By.id("username"));
        usernameWebElement.sendKeys(username);

        WebElement passswordWebElement = driver.findElement(By.name("password"));
        passswordWebElement.sendKeys(incorrectPassword);

        WebElement loginButtonWebElement = driver.findElement(By.tagName("button"));
        loginButtonWebElement.click();

        WebElement incorrectUsernameMessage = driver.findElement(By.xpath("//div[@id='flash']"));

        //Assertions
        Assert.assertTrue(incorrectUsernameMessage.getText().contains(expectedIncorrectMessage ),"Expected incorrect message is not contained in the displayed message.");

        driver.quit();
    }

    @Parameters({"username","password","expectedMessage"})
    @Test
    public void negativeLoginTests(String username, String password, String expectedMessage){
        String url = "https://the-internet.herokuapp.com/login";
        WebDriver driver = new ChromeDriver();

        driver.get(url);

        WebElement usernameWebElement = driver.findElement(By.id("username"));
        usernameWebElement.sendKeys(username);

        WebElement passswordWebElement = driver.findElement(By.name("password"));
        passswordWebElement.sendKeys(password);

        WebElement loginButtonWebElement = driver.findElement(By.tagName("button"));
        loginButtonWebElement.click();

        WebElement incorrectUsernameMessage = driver.findElement(By.xpath("//div[@id='flash']"));

        //Assertions
        Assert.assertTrue(incorrectUsernameMessage.getText().contains(expectedMessage ),"Expected incorrect message is not contained in the displayed message.");

        driver.quit();
    }

}
