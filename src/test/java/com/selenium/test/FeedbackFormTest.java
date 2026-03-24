package com.selenium.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class FeedbackFormTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Use WebDriverManager to handle ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // Run headless for CI/CD environments
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testFeedbackFormSubmission() {
        // Navigate to feedback page
        String projectPath = System.getProperty("user.dir");
        driver.get("file:///" + projectPath + "/feedback.html");

        // Fill form fields
        driver.findElement(By.id("studentName")).sendKeys("Sushmit Partakke");
        driver.findElement(By.id("email")).sendKeys("sushmit@gmail.com");
        driver.findElement(By.id("mobile")).sendKeys("9876543210");

        Select dept = new Select(driver.findElement(By.id("department")));
        dept.selectByVisibleText("Computer Science");

        driver.findElement(By.id("male")).click();

        driver.findElement(By.id("comments"))
              .sendKeys("This is a sample feedback comment with more than ten words for testing purpose.");

        // Submit
        driver.findElement(By.id("submitBtn")).click();

        // Wait for success indicator and verify
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")))
                                   .getText();

        Assert.assertTrue(successMessage.toLowerCase().contains("feedback submitted successfully"),
                         "Feedback submission should be successful");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}