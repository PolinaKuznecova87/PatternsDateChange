package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.patterns.DataGenerator.generateDate;

public class DataChange {

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
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        //$(".notification__content")
        //                .shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15))
        //                .shouldBe(Condition.visible);


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
