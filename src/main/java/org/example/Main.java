package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        // Установка пути к драйверу Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\annav\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Инициализация экземпляра WebDriver для управления браузером Chrome
        WebDriver driver = new ChromeDriver();

        // Открытие страницы
        driver.get("file:///C:/Users/annav/Downloads/Telegram%20Desktop/qa-test%20(1).html");
    }
}