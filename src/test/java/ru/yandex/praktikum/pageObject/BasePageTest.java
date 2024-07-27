package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.praktikum.pageObject.constants.BasePageConstants;

import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.pageObject.constants.BasePageConstants.*;

@RunWith(Parameterized.class)
public class BasePageTest {
    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final By question;
    private final By answer;
    private final By labelResult;
    private final String expected;

    public BasePageTest(By question, By answer, By labelResult, String expected) {
        this.question = question;
        this.answer = answer;
        this.labelResult = labelResult;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {QUESTION_1, ANSWER_1, ELEMENT_ANSWER_1, EXPECTED_ANSWER_1},
                {QUESTION_2, ANSWER_2, ELEMENT_ANSWER_2, EXPECTED_ANSWER_2},
                {QUESTION_3, ANSWER_3, ELEMENT_ANSWER_3, EXPECTED_ANSWER_3},
                {QUESTION_4, ANSWER_4, ELEMENT_ANSWER_4, EXPECTED_ANSWER_4},
                {QUESTION_5, ANSWER_5, ELEMENT_ANSWER_5, EXPECTED_ANSWER_5},
                {QUESTION_6, ANSWER_6, ELEMENT_ANSWER_6, EXPECTED_ANSWER_6},
                {QUESTION_7, ANSWER_7, ELEMENT_ANSWER_7, EXPECTED_ANSWER_7},
                {QUESTION_8, ANSWER_8, ELEMENT_ANSWER_8, EXPECTED_ANSWER_8},
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
    public void checkQuestion() {
        new BasePage(driver)
                .waitForLoadBasePage()
                .scrollToQuestions()
                .clickQuestion(question)
                .waitLoadAfterClickQuestion(labelResult);
        String result = driver.findElement(answer).getText();
        assertEquals(expected, result);
    }
}