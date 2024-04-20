package ru.netology.HW_Gradle_3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeTests {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void BeforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void AfterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void NegativeName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев123");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79501231234");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }

    @Test
    public void EmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79501231234");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    public void PhoneHaveNot11Symbols() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев-Кумач Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7950123123");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    public void PhoneWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев-Кумач Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79501231234");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    @Test
    public void EmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев-Кумач Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }

    @Test
    public void EmptyCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев-Кумач Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79501231234");
        driver.findElement(By.cssSelector("button")).click();
        boolean actual = driver.findElement(By.cssSelector(".input_invalid[data-test-id='agreement']")).isDisplayed();
        assertTrue(actual);
    }
}
