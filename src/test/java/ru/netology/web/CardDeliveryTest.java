package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    LocalDate date = LocalDate.now().plusDays(3);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String output = dtf.format(date);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
    @Test
    void shouldFormCardDeliveryTest() {
        $("[data-test-id=city] input").setValue("Самар").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("+79370000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + output));
    }

    @Test
    void shouldTestFieldCity() {
        $("[data-test-id=city] input").setValue("Сызрань").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("+79370000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("оставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestFieldData() {
        $("[data-test-id=city] input").setValue("Самара").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys("02.ju.2021");
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("+79370000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestFieldName() {
        $("[data-test-id=city] input").setValue("Самара").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Marina Oliynyk");
        $("[data-test-id=phone] input").setValue("+79370000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефис"));
    }

    @Test
    void shouldTestFieldPhone() {
        $("[data-test-id=city] input").setValue("Самара").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("89370000000");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 10 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestFieldAgreement() {
        $("[data-test-id=city] input").setValue("Самара").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("+79370000000");
        $("[data-test-id=agreement] .checkbox__text").shouldHave(exactText("  Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        $(withText("Забронировать")).click();
    }

    @Test
    void shouldTestFieldNonInputPhone() {
        $("[data-test-id=city] input").setValue("Самара").pressEnter();
        $("[data-test-id=date] input").doubleClick().sendKeys(output);
        $("[data-test-id=name] input").setValue("Марина Олийнык");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения!"));
    }
}

