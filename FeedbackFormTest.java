package com.selenium.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FeedbackFormTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("file:///C:/Users/sushm/Downloads/Sem_6/DevOps/SITFeedback/feedback.html");
            driver.manage().window().maximize();
            System.out.println("========== FEEDBACK FORM AUTOMATION TESTS ==========");

            testPageOpens(driver);
            testValidFeedbackSubmission(driver);
            testBlankRequiredFields(driver);
            testInvalidEmail(driver);
            testInvalidMobile(driver);
            testDepartmentDropdown(driver);
            testButtons(driver);

            System.out.println("========== ALL TESTS COMPLETED ==========");
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static void testPageOpens(WebDriver driver) throws InterruptedException {
        String title = driver.getTitle();
        if (title.contains("Student Feedback Registration Form")) {
            System.out.println("✅ Test 1: Page opens successfully: " + title);
        } else {
            System.err.println("❌ Test 1: Page title mismatch: " + title);
        }
    }

    public static void testValidFeedbackSubmission(WebDriver driver) throws InterruptedException {
        clearAndFillForm(driver,
                "Ankit Sharma",
                "ankit.sharma@example.com",
                "9876543210",
                "Computer Science",
                "Male",
                "This is a sample feedback comment with more than ten words to satisfy validation.");

        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(1000);

        WebElement success = driver.findElement(By.id("successMessage"));
        if (success.getAttribute("class").contains("show")) {
            System.out.println("✅ Test 2: Valid data submission success");
        } else {
            System.err.println("❌ Test 2: Expected success message not shown");
        }

        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);
    }

    public static void testBlankRequiredFields(WebDriver driver) throws InterruptedException {
        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(500);

        checkErrorField(driver, "nameError", "Name");
        checkErrorField(driver, "emailError", "Email");
        checkErrorField(driver, "mobileError", "Mobile");
        checkErrorField(driver, "departmentError", "Department");
        checkErrorField(driver, "genderError", "Gender");
        checkErrorField(driver, "commentsError", "Comments");

        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);
    }

    public static void testInvalidEmail(WebDriver driver) throws InterruptedException {
        clearAndFillForm(driver,
                "Priya Singh",
                "invalid-email",
                "9876543210",
                "Electronics",
                "Female",
                "This feedback has enough words to pass comment validation and only email is invalid.");

        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(500);

        checkErrorField(driver, "emailError", "Invalid Email");
        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);
    }

    public static void testInvalidMobile(WebDriver driver) throws InterruptedException {
        clearAndFillForm(driver,
                "Rahul Verma",
                "rahul.verma@example.com",
                "12345",
                "Mechanical",
                "Other",
                "Comments are long enough and mobile number is invalid for testing invalid mobile flow.");

        driver.findElement(By.id("submitBtn")).click();
        Thread.sleep(500);

        checkErrorField(driver, "mobileError", "Invalid Mobile");
        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);
    }

    public static void testDepartmentDropdown(WebDriver driver) throws InterruptedException {
        driver.findElement(By.id("department")).click();
        Select department = new Select(driver.findElement(By.id("department")));
        department.selectByValue("Civil");
        if ("Civil".equals(driver.findElement(By.id("department")).getAttribute("value"))) {
            System.out.println("✅ Test 5: Department dropdown works");
        } else {
            System.err.println("❌ Test 5: Department selection failed");
        }

        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);
    }

    public static void testButtons(WebDriver driver) throws InterruptedException {
        clearAndFillForm(driver,
                "Arjun Patel",
                "arjun.patel@example.com",
                "9998887776",
                "Information Technology",
                "Male",
                "Feedback for submit and reset button check with enough words for comments field.");

        driver.findElement(By.id("resetBtn")).click();
        Thread.sleep(500);

        boolean emptyName = driver.findElement(By.id("studentName")).getAttribute("value").isEmpty();
        if (emptyName) {
            System.out.println("✅ Test 6: Reset button works");
        } else {
            System.err.println("❌ Test 6: Reset button did not clear the form");
        }
    }

    private static void clearAndFillForm(WebDriver driver, String name, String emailVal, String mobileVal, String departmentVal, String genderVal, String commentsVal) {
        driver.findElement(By.id("resetBtn")).click();

        driver.findElement(By.id("studentName")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(emailVal);
        driver.findElement(By.id("mobile")).sendKeys(mobileVal);

        new Select(driver.findElement(By.id("department"))).selectByValue(departmentVal);
        driver.findElement(By.id(genderVal.toLowerCase())).click();
        driver.findElement(By.id("comments")).sendKeys(commentsVal);
    }

    private static void checkErrorField(WebDriver driver, String errorId, String fieldName) {
        WebElement error = driver.findElement(By.id(errorId));
        if (error.getAttribute("class").contains("show")) {
            System.out.println("   ✓ " + fieldName + " validation error displayed");
        } else {
            System.err.println("   ❌ " + fieldName + " validation error NOT displayed");
        }
    }
}
