package com.selenium.work;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class App {
    
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("    Selenium Web Application Testing");
        System.out.println("==========================================");
        
        task1();
        
        Task2.getPublicIP();
        
        Task3.getWeatherForecast();
        
        System.out.println("Все задания выполнены");
    }
    
    public static void task1() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        
        try {
            System.out.println("\nЗадание №1: Генератор паролей");
            webDriver.get("https://www.calculator.net/password-generator.html");
            
            WebElement passwordElement;
            
            try {
                passwordElement = webDriver.findElement(By.id("generated-password-result"));
                String generatedPassword = passwordElement.getAttribute("value");
                System.out.println("Сгенерированный пароль: " + generatedPassword);
            } catch (Exception e1) {
                try {
                    passwordElement = webDriver.findElement(By.cssSelector("#generated-password"));
                    String generatedPassword = passwordElement.getAttribute("value");
                    System.out.println("Сгенерированный пароль: " + generatedPassword);
                } catch (Exception e2) {
                    java.util.List<WebElement> inputs = webDriver.findElements(By.tagName("input"));
                    for (WebElement input : inputs) {
                        String type = input.getAttribute("type");
                        if ("text".equals(type) && input.isDisplayed()) {
                            String value = input.getAttribute("value");
                            if (value != null && value.length() >= 8) {
                                System.out.println("Сгенерированный пароль: " + value);
                                break;
                            }
                        }
                    }
                }
            }
            
            System.out.println("Страница загружена");
            System.out.println();
            
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("Ошибка" + e.toString());
        } finally {
            webDriver.quit();
        }
    }
}