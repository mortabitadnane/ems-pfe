package com.maroc_ouvrage.semployee.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    void testLoginSuccess() throws InterruptedException {


        driver.get("http://localhost:4200/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for username input and type
        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        usernameInput.sendKeys("johndoe");

        // Wait for the real password input inside <p-password> and type
        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#password input[type='password']") // selects the nested input
        ));
        passwordInput.sendKeys("1234");

        // Click login button
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginBtn")));
        loginBtn.click();

        Thread.sleep(2000);


        WebElement greeting = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Good')]") // or '{{ greeting }}' rendered text
                ));

        assertTrue(greeting.isDisplayed(), "Dashboard greeting should be visible");

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
