package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderModalPage {
    private final WebDriver driver;

    // Кнопка "Да" в модальном окне
    private final By confirmYesButton = By.xpath(".//button[text()='Да']");

    // Заголовок "Хотите оформить заказ?"
    private final By confirmationTitle = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");

    // Заголовок "Заказ оформлен"
    private final By successTitle = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");

    // Номер заказа
    private final By orderNumber = By.xpath(".//div[contains(@class, 'Order_Text')]");

    public OrderModalPage(WebDriver driver) {
        this.driver = driver;
    }

    // Нажать "Да" в модальном окне подтверждения
    public void clickConfirmYes() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        driver.findElement(confirmYesButton).click();
    }

    // Проверить, что появилось модальное окно подтверждения
    public boolean isConfirmationModalVisible() {
        try {
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(confirmationTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Проверить, что появилось модальное окно успешного оформления
    public boolean isSuccessModalVisible() {
        try {
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOfElementLocated(successTitle));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Получить номер заказа
    public String getOrderNumber() {
        try {
            return driver.findElement(orderNumber).getText();
        } catch (Exception e) {
            return "Номер заказа не найден";
        }
    }

    // Подтвердить заказ
    public void confirmOrder() {
        if (isConfirmationModalVisible()) {
            clickConfirmYes();
        }
    }

    // Ожидание успешного оформления заказа
    public void waitForOrderSuccess() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(successTitle));
    }
}