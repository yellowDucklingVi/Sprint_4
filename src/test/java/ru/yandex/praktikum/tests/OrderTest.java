package ru.yandex.praktikum.tests;

/**
 * Тест оформления заказа самоката.
 * В Chrome присутствует баг - после подтверждения заказа
 * не появляется второе модальное окно с сообщением "Заказ оформлен".
 * Тест упадёт на строке orderModal.waitForOrderSuccess().
 * В браузере Firefox заказ оформляется успешно
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pages.MainPage;
import ru.yandex.praktikum.pages.OrderPage;
import ru.yandex.praktikum.pages.OrderModalPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;
    private OrderModalPage orderModal;

    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String comment;
    private final boolean useTopButton;

    public OrderTest(String name, String surname, String address,
                     String phone, String comment, boolean useTopButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][] {
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+79991234567", "Позвонить за час", true},
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+79991234567", "Позвонить за час", false},
                {"Мария", "Петрова", "пр. Мира, д. 25", "+79998765432", "Оставить у двери", true},
                {"Мария", "Петрова", "пр. Мира, д. 25", "+79998765432", "Оставить у двери", false}
        });
    }

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
    public void testOrderScooter() {
        if (useTopButton) {
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

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}