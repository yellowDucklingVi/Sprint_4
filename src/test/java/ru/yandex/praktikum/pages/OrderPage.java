package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderPage {
    private final WebDriver driver;

    // Поле "Имя"
    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");

    // Поле "Фамилия"
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");

    // Поле "Адрес"
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле "Станция метро"
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");

    // Первая станция метро в списке
    private final By firstMetroOption = By.xpath(".//div[@class='select-search__select']//li[1]");

    // Поле "Телефон"
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Поле "Когда привезти самокат"
    private final By deliveryDateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Срок аренды - выпадающий список
    private final By rentalPeriodField = By.className("Dropdown-placeholder");

    // Опция "сутки"
    private final By oneDayOption = By.xpath(".//div[@class='Dropdown-option' and text()='сутки']");

    // Чекбокс цвета "чёрный жемчуг"
    private final By blackCheckbox = By.id("black");

    // Поле "Комментарий для курьера"
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать"
    private final By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setSurname(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void selectMetroStation() {
        driver.findElement(metroField).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(firstMetroOption));
        driver.findElement(firstMetroOption).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNext() {
        driver.findElement(nextButton).click();
    }

    public void setDeliveryDateTomorrow() {
        // Получаем завтрашнюю дату
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String tomorrowFormatted = tomorrow.format(formatter);

        // Кликаем в поле даты
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.click();

        // Вводим завтрашнюю дату
        dateField.sendKeys(tomorrowFormatted);
        dateField.sendKeys(Keys.ENTER);
    }

    public void selectRentalPeriodOneDay() {
        driver.findElement(rentalPeriodField).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(oneDayOption));
        driver.findElement(oneDayOption).click();
    }

    public void selectBlackColor() {
        WebElement checkbox = driver.findElement(blackCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void fillFirstPage(String name, String surname, String address, String phone) {
        setName(name);
        setSurname(surname);
        setAddress(address);
        selectMetroStation();
        setPhone(phone);
        clickNext();

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodField));
    }

    public void fillSecondPage(String comment) {
        setDeliveryDateTomorrow();
        selectRentalPeriodOneDay();
        selectBlackColor();
        setComment(comment);
        clickOrderButton();
    }

    public boolean isSecondPageLoaded() {
        try {
            return driver.findElement(rentalPeriodField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}