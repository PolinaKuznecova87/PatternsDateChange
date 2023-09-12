package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;


import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

import static ru.netology.patterns.DataGenerator.generateDate;

public class DataChange {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");

        int daysToAddForFirstMeeting = 4;// плюс 4 дня от текущей
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;// плюс 7 дней от текущей
        var secondMeetingDate = generateDate(daysToAddForSecondMeeting);



        $$("[type=text]").filter(Condition.visible).first().setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[name=name]").setValue(validUser.getName());
        $("[name=phone]").setValue(validUser.getPhone());
        $(".checkbox__box").click();
        $x("//*[contains(text(), 'Запланировать')]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + firstMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);

        $x("//*[contains(text(), 'Запланировать')]").click();

        $x("//*[contains(text(), 'Перепланировать')]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + secondMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);


    }
}
