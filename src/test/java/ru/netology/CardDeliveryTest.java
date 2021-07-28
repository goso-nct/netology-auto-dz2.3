package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardDeliveryTest {

    SelenideElement element;
    DataGenerator data = new DataGeneratorImplArray();

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        $("[data-test-id=city] input").setValue(data.generateCity());

        String date = data.generateDate(5);
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue(data.generateName());
        $("[data-test-id=phone] input").setValue(data.generatePhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        element = $("[data-test-id=success-notification] .notification__content");
        element.shouldBe(visible);
        assertEquals("Встреча успешно запланирована на " + date, element.getText());

        date = data.generateDate(8);
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[role=button] .button__content").click();

        element = $("[data-test-id=replan-notification] .notification__content");
        element.shouldBe(visible);
        assertTrue(element.getText().contains("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $(byText("Перепланировать")).click();

        element = $("[data-test-id=success-notification] .notification__content");
        element.shouldBe(visible);
        assertEquals("Встреча успешно запланирована на " + date, element.getText());
    }

}
