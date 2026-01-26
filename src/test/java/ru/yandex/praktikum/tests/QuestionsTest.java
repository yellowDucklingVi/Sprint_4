package ru.yandex.praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pages.MainPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.assertTrue;

public class QuestionsTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        // Указываем путь к chromedriver вручную
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://qa-scooter.praktikum-services.ru/");

        mainPage = new MainPage(driver);
        mainPage.waitForPageLoad();
    }

    @Test
    public void testAllQuestionsExpand() {
        for (int i = 0; i < 8; i++) {
            mainPage.clickQuestion(i);

            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("(//div[contains(@class, 'accordion__panel')])[" + (i + 1) + "]")
                    ));

            boolean isVisible = mainPage.isAnswerVisible(i);
            assertTrue("Ответ на вопрос " + i + " не отображается", isVisible);

            String answerText = mainPage.getAnswerText(i);
            assertTrue("Текст ответа на вопрос " + i + " пустой",
                    answerText != null && !answerText.isEmpty());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}