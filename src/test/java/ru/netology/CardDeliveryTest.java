package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardDeliveryTest {

    DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");
    String firstMeetingDate = DataGenerator.generateDate(4);
    String secondMeetingDate = DataGenerator.generateDate(7);
    SelenideElement element;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @RepeatedTest(3)
    void shouldSuccessfulPlanAndReplanMeeting() {
        System.out.println(user + ", d1=" + firstMeetingDate + ", d2=" + secondMeetingDate);

        $("[data-test-id=city] input").setValue(user.getCity());

        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);

        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        element = $("[data-test-id=success-notification] .notification__content");
        element.shouldBe(visible);
        assertEquals("Встреча успешно запланирована на " + firstMeetingDate, element.getText());

        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $("[role=button] .button__content").click();

        element = $("[data-test-id=replan-notification] .notification__content");
        element.shouldBe(visible);
        assertTrue(element.getText().contains("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $(byText("Перепланировать")).click();

        element = $("[data-test-id=success-notification] .notification__content");
        element.shouldBe(visible);
        assertEquals("Встреча успешно запланирована на " + secondMeetingDate, element.getText());
    }

}
