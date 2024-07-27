package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.praktikum.pageObject.constants.OrderButtonCreate.DOWN_BUTTON;
import static ru.yandex.praktikum.pageObject.constants.OrderButtonCreate.UP_BUTTON;
import static ru.yandex.praktikum.pageObject.constants.CollorsScooter.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final String name;

    private final String surname;
    private final String address;
    private final int stateMetroNumber;
    private final String telephoneNumber;
    private final String date;
    private final String duration;
    private final Enum colour;
    private final String comment;
    private final String expectedHeader = "Заказ оформлен";
    private final Enum button;

    public OrderCreateTest(Enum button, String name, String surname, String address, int stateMetroNumber, String telephoneNumber,
                           String date, String duration, Enum colour, String comment) {
        this.button = button;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.stateMetroNumber = stateMetroNumber;
        this.telephoneNumber = telephoneNumber;
        this.date = date;
        this.duration = duration;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {UP_BUTTON, "Дмитрий", "Петров", "Пушкина", 123, "79802341532", "29.07.2024", "сутки", GREY, "Пожалуйста, привезите целый самокат"},
                {UP_BUTTON, "Иван", "Иванов", "Колотушкина", 7, "75242342342", "31.07.2024", "двое суток", BLACK, "Обязательно наличие крепления для смартфона"},
                {UP_BUTTON, "Игорь", "Сидоров", "Тверская", 10, "79802341563", "1.03.2020", "четверо суток", BLACK, "Домофон не работает, позвоните заранее спущусь вниз к двери"},
                {DOWN_BUTTON, "Дмитрий", "Петров", "Пушкина", 123, "79652569854", "21.09.2022", "пятеро суток", GREY, "Позвонить за 2 часа до выезда"},
                {DOWN_BUTTON, "Иван", "Иванов", "Колотошкина", 7, "79353479843", "25.11.2021", "шестеро суток", BLACK, "Проше привезти без царапин"},
                {DOWN_BUTTON, "Игорь", "Сидоров", "Тверская", 10, "79483454374", "28.09.2022", "семеро суток", BLACK, "Привезите с нормальным протектором, с лысой резиной не возьму!!!"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(site);
    }

    @After
    public void down() {
        driver.quit();
    }

    @Test
    public void testCreateOrderWithUpButton() {
        new BasePage(driver)
                .waitForLoadBasePage()
                .clickCreateOrderButton(button);

        new AboutRenter(driver)
                .waitForLoadOrderPage()
                .nameInput(name)
                .surnameInput(surname)
                .adressInput(address)
                .chooseMetro(stateMetroNumber)
                .telephoneInput(telephoneNumber)
                .clickNextButton();

        new AboutScooter(driver)
                .waitAboutRentHeader()
                .inputDate(date)
                .inputDuration(duration)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        PopUpWindow popUpWindow = new PopUpWindow(driver);
                popUpWindow.yesButtonClick();

        assertTrue(popUpWindow.headerGetAfterOrder().contains(expectedHeader));
    }
}
