package ru.netology.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CallbackMyVTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void shouldSubmitTheApplicationSuccessfully() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        // Из за наличия задержки возникновения отчётного сообщения, использование cssSelector приводит к падению теста.
        // В связи с этим принято решение использовать className: "paragraph".
        //WebElement message = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        WebElement message = driver.findElement(By.className("paragraph"));
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.getText().trim());

    }


}
