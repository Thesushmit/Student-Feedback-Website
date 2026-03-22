package com.selenium.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class FristPro {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\sushm\\Downloads\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Feedback page path (use one of the two depending your current HTML)
        // registration:
        // driver.get("file:///C:/Users/sushm/Downloads/Sem_6/DevOps/SITLOGIN/registration.html");
        // feedback:
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

        // Wait for success indicator
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean passed = false;

        try {
            // feedback form uses #successMessage
            String success = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")))
                                 .getText();

            if (success.toLowerCase().contains("feedback submitted successfully")) {
                passed = true;
            }
        } catch (Exception e) {
            passed = false;
        }

        if (passed) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        driver.quit();
    }
}