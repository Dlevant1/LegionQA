import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FirstSeleniumTest {

    private static final String HOME_PAGE_URL = "https://www.facebook.com/";

    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.get(HOME_PAGE_URL);
    }

    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();

    }

    @AfterEach
    public void testTeardown() {
        driver.get(HOME_PAGE_URL);
    }

    @Test
    public void homePageURLTest() {
        String actualURL = driver.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, actualURL, "URLs do not match");
    }

    @Test
    public void findEmailTest() {
        //WebElement element = driver.findElement(By.id("email"));
        //WebElement element = driver.findElement(By.name("email"));
        //WebElement element = driver.findElement(By.linkText("Create a Page"));
        //WebElement element = driver.findElement(By.partialLinkText("a Page"));
        //WebElement element = driver.findElement(By.cssSelector("#email"));
        List<WebElement> element = driver.findElements(By.className("inputtext"));
        System.out.println(element.size());
        assertNotNull(element);
    }

    @Test
    public void findElemetsByXpathTest() {
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal_pass']"));
        assertNotNull(passwordElement);
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);
        WebElement forgotPassElement = driver.findElement(By.xpath("//a[text()='Forgot password?']"));
        assertNotNull(forgotPassElement);

        WebElement createNewAccButton = driver.findElement(By.xpath("//a[text()='Create new account']"));
        assertNotNull(createNewAccButton);
    }

    @Test
    public void loginScreenTest() {
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='email']"));
        assertNotNull(emailElement);
        emailElement.sendKeys("levant.dayvid@gmail.com");
        String emailValue = emailElement.getAttribute("value");
        assertEquals("levant.dayvid@gmail.com", emailValue);

        WebElement passwordElement = driver.findElement(By.xpath("//input[@data-testid='royal_pass']"));
        assertNotNull(passwordElement);
        passwordElement.sendKeys("25547dayvid");
        String passValue = passwordElement.getAttribute("value");
        assertEquals("25547dayvid", passValue);
        WebElement loginButtonElement = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(loginButtonElement);

        loginButtonElement.click();
    }

    @Test
    public void signupScreenTest() {
        // Create an instance of WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Create new account']"))).click();

        // Wait for the popup to load and the first name input to be visible
        WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstname']")));
        assertNotNull(firstNameElement);
        firstNameElement.sendKeys("David");
        assertEquals("David", firstNameElement.getAttribute("value"));

        // Fill in the last name
        WebElement lastNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='lastname']")));
        assertNotNull(lastNameElement);
        lastNameElement.sendKeys("Levant");
        assertEquals("Levant", lastNameElement.getAttribute("value"));

        //Fill in the email
        WebElement EmailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']")));
        assertNotNull(EmailElement);
        EmailElement.sendKeys("d.levant@gmail.com");
        assertEquals("d.levant@gmail.com", EmailElement.getAttribute("value"));

        // Fill in the password
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_passwd__']")));
        assertNotNull(passwordElement);
        passwordElement.sendKeys("Password123!");
        assertEquals("Password123!", passwordElement.getAttribute("value"));

        // Click Custom Gender to display additional text boxes
        WebElement customGenderElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Custom']")));
        assertNotNull(customGenderElement);
        customGenderElement.click();

        // Wait for the custom gender text box to be visible
        WebElement customGenderTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='custom_gender']")));
        assertTrue(customGenderTextBox.isDisplayed());

        // Click the Sign Up button
        WebElement signUpButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='websubmit']")));
        assertNotNull(signUpButtonElement);
        signUpButtonElement.click();

    }

    @Test
    public void testEmptyFields() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Create new account']")));
        createAccountButton.click();

        // Try to submit without filling any fields
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='websubmit']")));
        signUpButton.click();

        // Validate that the page remains on the signup form
        assertTrue(driver.getCurrentUrl().contains("facebook.com/r.php"));
    }

    @Test
    public void testInvalidEmailFormat() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Create new account']")));
        createAccountButton.click();

        // Fill in valid data but with an invalid email
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("John");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Doe");
        WebElement emailElement = driver.findElement(By.xpath("//input[@name='reg_email__']"));
        emailElement.sendKeys("invalid-email-format");
        WebElement passwordElement = driver.findElement(By.xpath("//input[@name='reg_passwd__']"));
        passwordElement.sendKeys("Password123!");
        WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='websubmit']")));
        signUpButton.click();
        assertTrue(driver.getCurrentUrl().contains("facebook.com/r.php"));
    }

    @Test
    public void testLongTextInputs() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Create new account']")));
        createAccountButton.click();

        // Fill in the form with long text
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("A very long first name that exceeds normal length");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("A very long last name that exceeds normal length");
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("long.email@example.com");
        driver.findElement(By.xpath("//input[@name='reg_passwd__']")).sendKeys("LongPassword111123!");
        WebElement signUpButton = driver.findElement(By.xpath("//button[@name='websubmit']"));
        signUpButton.click();
        assertTrue(driver.getCurrentUrl().contains("facebook.com/r.php"));



    }

    @Test
    public void testSpecialCharactersInInput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement createAccountButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Create new account']")));
        createAccountButton.click();

        // Fill in the form with special characters
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("John!@#");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Doe$%^");
        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("john.doe@example.com");
        driver.findElement(By.xpath("//input[@name='reg_passwd__']")).sendKeys("Password123!");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@name='websubmit']"));
        signUpButton.click();

        assertTrue(driver.getCurrentUrl().contains("facebook.com/r.php"));
    }


}

