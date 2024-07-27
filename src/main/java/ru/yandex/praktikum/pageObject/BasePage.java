package ru.yandex.praktikum.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.yandex.praktikum.pageObject.constants.OrderButtonCreate.DOWN_BUTTON;
import static ru.yandex.praktikum.pageObject.constants.OrderButtonCreate.UP_BUTTON;

public class BasePage {
    WebDriver driver;
    private final By homeHeader = By.className("Home_Header__iJKdX");
    private final By upOrderButton = By.className("Button_Button__ra12g");
    private final By downOrderButton2 = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By questionsHeader = By.className("Home_FourPart__1uthg");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    //метод ожидания загрузки главной страницы
    public BasePage waitForLoadBasePage() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver -> (driver.findElement(homeHeader).getText() != null
                && !driver.findElement(homeHeader).getText().isEmpty()
        ));
        return this;
    }

    //метод ожидания загрузки ответа на вопрос
    public void waitLoadAfterClickQuestion(By accordionLabel) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(accordionLabel).getText() != null
    && !driver.findElement(accordionLabel).getText().isEmpty()
        ));
    }

    //метод прокрутки к блоку "Вопросы о важном"
    public BasePage scrollToQuestions() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(questionsHeader));
        return this;
    }

    //метод прокрутки ко второй кнопке "Заказать"
    public BasePage scrollToDownOrderButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(downOrderButton2));
        return this;
    }

    public BasePage clickUpOrderButton() {
        driver.findElement(upOrderButton).click();
        return this;
    }

    public BasePage clickDownOrderButton() {
        driver.findElement(downOrderButton2).click();
        return this;
    }

    public void clickCreateOrderButton(Enum button) {
        if (button.equals(UP_BUTTON)) {
            clickUpOrderButton();
        } else if (button.equals(DOWN_BUTTON)) {
            scrollToDownOrderButton();
            clickDownOrderButton();
        }
    }

    public BasePage clickQuestion(By question) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(question))
                .click();
        return this;
    }
}
