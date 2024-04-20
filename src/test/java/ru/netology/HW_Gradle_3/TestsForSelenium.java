package ru.netology.HW_Gradle_3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsForSelenium {

    private WebDriver driver;
    
    @BeforeAll
    public static void setupAll() {
       //WebDriverManager WebDriverManager = null;
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void BeforeEach(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void AfterEach(){
        driver.quit();
        driver = null;
    }

    @Test
    public void SuccessfulForm(){
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Лебедев-Кумач Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79501231234");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);

    }
}
