package com.selenium.work;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    
    public static String getPublicIP() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        String ipAddress = null;
        
        try {
            System.out.println("\n=== Задание №2: Получение IP адреса ===");
            webDriver.get("https://api.ipify.org/?format=json");
            
            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            ipAddress = (String) jsonObject.get("ip");
            
            System.out.println("Ваш публичный IP адрес: " + ipAddress);
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("Ошибка при получении IP адреса: " + e.toString());
        } finally {
            webDriver.quit();
        }
        
        return ipAddress;
    }
}