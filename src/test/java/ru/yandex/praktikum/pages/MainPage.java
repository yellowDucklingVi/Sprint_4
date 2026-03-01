package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    // Константа URL
    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    private final WebDriver driver;

    // Кнопка "Заказать" в хедере
    private final By orderTopButton = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");

    // Кнопка "Заказать" внизу страницы
    private final By orderBottomButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    // Заголовок страницы
    private final By pageTitle = By.xpath("//div[contains(@class, 'Home_Header')]");

    // Локаторы для вопросов
    private final String questionLocatorTemplate = "(//div[@data-accordion-component='AccordionItemButton'])[%d]";
    private final String answerLocatorTemplate = "(//div[@data-accordion-component='AccordionItemPanel'])[%d]";

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }


    public void clickOrderTopButton() {
        driver.findElement(orderTopButton).click();
    }

    public void clickOrderBottomButton() {
        WebElement button = driver.findElement(orderBottomButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }


    public void clickQuestion(int index) {
        By questionLocator = By.xpath(String.format(questionLocatorTemplate, index + 1));
        WebElement question = driver.findElement(questionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        question.click();
    }

    public String getAnswerText(int index) {
        By answerLocator = By.xpath(String.format(answerLocatorTemplate, index + 1));
        WebElement answer = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText();
    }
}