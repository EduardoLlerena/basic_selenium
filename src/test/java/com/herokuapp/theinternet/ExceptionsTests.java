package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionsTests {
    private WebDriver driver;
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser){
        String url = "https://practicetestautomation.com/practice-test-exceptions/";
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
        //Implicit wait
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //go to website
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

    @Test
    //Delete the wait to force the no such element exception
    public void noSuchElementException(){
        WebElement addRowWebElement = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        addRowWebElement.click();

        //ExplicitWait
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement secondRowWebElement= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        Assert.assertTrue(secondRowWebElement.isDisplayed(),"Row 2 is not displayed.");
    }

    @Test
    //Search element by name to cause the element not interactable exception, because it will return the first save button which is not visible
    public void elementNotInteractableException(){
        WebElement addRowWebElement = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        addRowWebElement.click();

        //ExplicitWait
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement secondRowWebElement= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        secondRowWebElement.sendKeys("Sushi");

        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        WebElement editButton = driver.findElement(By.xpath("//div[@id='row2']/button[@id='edit_btn']"));

        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"confirmation\"]")));
        Assert.assertEquals(confirmationMessage.getText(),"Row 2 was saved","Text is not as expected");
        Assert.assertTrue(confirmationMessage.isDisplayed(),"Confirmation message is not displayed");
        Assert.assertTrue(editButton.isDisplayed());
        Assert.assertFalse(saveButton.isDisplayed());
        Assert.assertFalse(secondRowWebElement.isEnabled());
    }

    @Test
    //Delete the steps to click on the edit button to force the invalid element state exception
    public void invalidElementStateException(){
        WebElement firstRow = driver.findElement(By.xpath("//*[@id=\"row1\"]/input"));

        WebElement editButtonRow1 = driver.findElement(By.xpath("//*[@id='row1']/button[@id='edit_btn']"));
        editButtonRow1.click();

        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(firstRow));
        firstRow.clear();
        firstRow.sendKeys("Pasta");

        WebElement saveButton = driver.findElement(By.xpath("//*[@id='row1']/button[@id='save_btn']"));
        saveButton.click();

        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"confirmation\"]")));
        Assert.assertEquals(confirmationMessage.getText(),"Row 1 was saved","Text in confirmation message is not as expected");
        Assert.assertEquals(firstRow.getAttribute("value"),"Pasta","Value in input 1 is not expected");
        Assert.assertTrue(editButtonRow1.isDisplayed());
        Assert.assertTrue(confirmationMessage.isDisplayed(),"Confirmation message is not displayed");
        Assert.assertFalse(firstRow.isEnabled());
        Assert.assertFalse(saveButton.isDisplayed());

    }

    @Test
    //To force the exception, uncomment the first assertion because the element is no longer available
    public void staleElementReferenceException(){
        WebElement instructionsWebElement = driver.findElement(By.id("instructions"));

        WebElement addRowWebElement = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        addRowWebElement.click();

        //Assert.assertFalse(instructionsWebElement.isDisplayed());

        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))),"Instructions are still displayed");
    }

    @Test
    //To force timeout change wait time to less than 5 seconds
    public void timeoutException(){
        WebElement addRowWebElement = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        addRowWebElement.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement secondRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        Assert.assertTrue(secondRow.isDisplayed());
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
