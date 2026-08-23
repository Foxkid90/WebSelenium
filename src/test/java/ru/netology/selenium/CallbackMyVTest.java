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

    // Задача № 1 - Пункт 1.1 - Позитивный сценарий
    @Test
    void shouldSubmitTheApplicationSuccessfullyItem11() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        assertTrue(message.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.getText().trim());

    }

    // Задача № 1 - Пункт 1.2 - Позитивный сценарий: дефис в поле "Фамилия и имя"
    @Test
    void shouldSubmitTheApplicationSuccessfullyItem12() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Барклай-де-Толли Михаил");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+78120001814");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        assertTrue(message.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.getText().trim());

    }

    // Задача № 1 - Пункт 1.3 - Позитивный сценарий: буква "ё" - РЕПОРТ
    @Test
    void shouldSubmitTheApplicationSuccessfullyItem13() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Пётр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        assertTrue(message.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.getText().trim());

    }

    // Задача № 2 - Пункт 2.1 - Негативный сценарий: латинские буквы в поле "Фамилия и имя"
    @Test
    void shouldDisplayErrorWithTextMessageItem21() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Vrangel Petr");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=name] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", message.getText().trim());

    }

    // Задача № 2 - Пункт 2.2 - Негативный сценарий: спецсимвол (кроме дефиса и пробела) в поле "Фамилия и имя"
    @Test
    void shouldDisplayErrorWithTextMessageItem22() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр%");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=name] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", message.getText().trim());

    }

    // Задача № 2 - Пункт 2.3 - Негативный сценарий: пустое поле "Фамилия и имя"
    @Test
    void shouldDisplayErrorWithTextMessageItem23() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=name] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Поле обязательно для заполнения", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.1 - Негативный сценарий: граничные значения - 10 цифр (11 символов) в поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem31() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+7917000192");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.2 - Негативный сценарий: граничные значения - 12 цифр (13 символов) в поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem32() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+791700019225");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.3 - Негативный сценарий: Номер телефона без "+" в поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem33() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.4 - Негативный сценарий: "+" не на первом месте номера телефона в поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem34() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("7+9170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.5 - Негативный сценарий: любые символы, отличные от цифр и "+" в поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem35() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922Ж");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.getText().trim());

    }

    // Задача № 2 - Пункт 3.6 - Негативный сценарий: пустое поле "Мобильный телефон"
    @Test
    void shouldDisplayErrorWithTextMessageItem36() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement message = driver.findElement(By.cssSelector("span[data-test-id=phone] span.input__sub"));
        assertTrue(message.isDisplayed());
        assertEquals("Поле обязательно для заполнения", message.getText().trim());

    }

    // Задача № 2 - Пункт 4 - Негативный сценарий: пустой "Чек-бокс"
    @Test
    void shouldDisplayErrorWithTextMessageItem4() {
        WebElement form = driver.findElement(By.cssSelector("form[enctype=\"application/x-www-form-urlencoded\"]"));
        form.findElement(By.cssSelector("span[data-test-id=name] input[value]")).sendKeys("Врангель Петр");
        form.findElement(By.cssSelector("span[data-test-id=phone] input[value]")).sendKeys("+79170001922");
        form.findElement(By.cssSelector("label[data-test-id=agreement] span[class=checkbox__box]"));
        form.findElement(By.cssSelector("button[type=button]")).click();
        WebElement colorText = driver.findElement(By.cssSelector("label.input_invalid span.checkbox__text"));
        assertTrue(colorText.isDisplayed());
        assertEquals("rgba(255, 92, 92, 1)", colorText.getCssValue("color"));

    }

}
