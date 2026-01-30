package ru.yandex.praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.pages.OrderPage;
import ru.yandex.praktikum.pages.OrderModalPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private OrderPage orderPage;
    private OrderModalPage orderModal;

    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String comment;
    private final String buttonType;

    public OrderTest(String name, String surname, String address,
                     String phone, String comment, String buttonType) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.buttonType = buttonType;
    }

    @Parameterized.Parameters(name = "Заказ через {5} кнопку: {0} {1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+79991234567", "Позвонить за час", "верхнюю"},
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+79991234567", "Позвонить за час", "нижнюю"},
                {"Мария", "Петрова", "пр. Мира, д. 25", "+79998765432", "Оставить у двери", "верхнюю"},
                {"Мария", "Петрова", "пр. Мира, д. 25", "+79998765432", "Оставить у двери", "нижнюю"}
        });
    }

    @Before
    public void setUp() {
        super.setUp();
        mainPage.waitForPageLoad();
    }

    @Test
    public void testOrderScooter() {
        if ("верхнюю".equals(buttonType)) {
            mainPage.clickOrderTopButton();
        } else {
            mainPage.clickOrderBottomButton();
        }

        orderPage = new OrderPage(driver);
        orderPage.fillFirstPage(name, surname, address, phone);

        assertTrue("Не удалось перейти на вторую страницу формы",
                orderPage.isSecondPageLoaded());

        orderPage.fillSecondPage(comment);

        orderModal = new OrderModalPage(driver);
        orderModal.confirmOrder();
        orderModal.waitForOrderSuccess();

        boolean isSuccess = orderModal.isSuccessModalVisible();
        assertTrue("Заказ не оформлен успешно", isSuccess);
    }
}