package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Register {

    WebDriver driver;

    // ---------- SETUP ----------
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void openRegisterPage() {
        driver.get("http://127.0.0.1:5500/register.html");
    }

    // ---------- COMMON ACTION ----------
    public void enterDetails(String username, String email, String password, String confirmPassword) {

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("confirmPassword")).clear();

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(confirmPassword);

        driver.findElement(By.id("registerBtn")).click();

        // 🔥 HANDLE ALERT SAFELY
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert appeared (for negative test cases)
        }
    }
    // ---------- VERIFICATION METHODS ----------

    public void verifySuccess() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("index")) {
            System.out.println("PASS: Registration Successful");
        } else {
            System.out.println("FAIL: Registration Failed");
        }
    }

    public void verifyErrorMessage(String testCaseName) {
        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("login")) {
            System.out.println("PASS: " + testCaseName);
        } else {
            System.out.println("FAIL: " + testCaseName);
        }
    }

    // ---------- TEST CASES ----------

    public void positiveTest() {
        System.out.println("Running Positive Test Case...");
        enterDetails("validuser01", "validuser01@gmail.com", "Password@123", "Password@123");
        verifySuccess();
    }

    public void emptyFieldsTest() {
        System.out.println("Running Empty Fields Test Case...");
        enterDetails("", "", "", "");
        verifyErrorMessage("Empty Fields Validation");
    }

    public void invalidEmailTest() {
        System.out.println("Running Invalid Email Test Case...");
        enterDetails("user02", "invalidemail", "Password@123", "Password@123");
        verifyErrorMessage("Invalid Email Validation");
    }

    public void passwordMismatchTest() {
        System.out.println("Running Password Mismatch Test Case...");
        enterDetails("user03", "user03@gmail.com", "Password@123", "Password@999");
        verifyErrorMessage("Password Mismatch Validation");
    }

    public void shortPasswordTest() {
        System.out.println("Running Short Password Test Case...");
        enterDetails("user04", "user04@gmail.com", "123", "123");
        verifyErrorMessage("Short Password Validation");
    }

    // ---------- TEARDOWN ----------
    public void closeBrowser() {
        driver.quit();
    }

    // ---------- TEST RUNNER ----------
    public static void main(String[] args) throws InterruptedException{

        Register test = new Register();

        test.openBrowser();
        test.openRegisterPage();

        // Positive Test
        test.positiveTest();
        Thread.sleep(2000);

        // Go back to register page for next test
        test.openRegisterPage();
        test.emptyFieldsTest();
        Thread.sleep(2000);

        test.openRegisterPage();
        test.invalidEmailTest();
        Thread.sleep(2000);

        test.openRegisterPage();
        test.passwordMismatchTest();
        Thread.sleep(2000);

        test.openRegisterPage();
        test.shortPasswordTest();
        Thread.sleep(2000);

        test.closeBrowser();

        System.out.println("All Register Test Cases Executed.");
    }
}
