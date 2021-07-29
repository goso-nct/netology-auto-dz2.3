package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardDeliveryTest {

    DataGenerator.UserInfo user;
    String firstMeetingDate;
    String secondMeetingDate;
    SelenideElement element;
    List<String> report = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        user = DataGenerator.Registration.generateUser("ru");
        firstMeetingDate = DataGenerator.generateDate(3, 5);
        secondMeetingDate = DataGenerator.generateDate(5, 10);
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @RepeatedTest(5)
    void shouldSuccessfulPlanAndReplanMeeting(RepetitionInfo repetitionInfo) {
        report.add("" + repetitionInfo.getCurrentRepetition() + ". "
                + user + ", d1=" + firstMeetingDate + ", d2=" + secondMeetingDate);

        $("[data-test-id=city] input").setValue(user.getCity());

        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);

        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        if (!$(byText("Доставка в выбранный город недоступна")).isDisplayed()) {

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
        } else {
            report.add("Город вне списка!");
        }
    }

    @AfterAll
    void printReport() {
        report.forEach(System.out::println);
    }
}
