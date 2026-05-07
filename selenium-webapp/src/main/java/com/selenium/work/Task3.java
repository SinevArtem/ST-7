package com.selenium.work;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Task3 {
    
    public static void getWeatherForecast() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        
        try {
            System.out.println("\nЗадание №3: Прогноз погоды для Нижнего Новгорода");
            
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
            
            webDriver.get(url);
            
            WebElement preElement = webDriver.findElement(By.tagName("pre"));
            String jsonString = preElement.getText();
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            
            JSONObject hourly = (JSONObject) jsonObject.get("hourly");
            JSONArray timeArray = (JSONArray) hourly.get("time");
            JSONArray temperatureArray = (JSONArray) hourly.get("temperature_2m");
            JSONArray rainArray = (JSONArray) hourly.get("rain");
            
            System.out.println("\n┌────┬─────────────────────┬─────────────┬─────────────┐");
            System.out.println("│ №  │ Дата/время          │ Температура │ Осадки (мм) │");
            System.out.println("├────┼─────────────────────┼─────────────┼─────────────┤");
            
            // Путь к папке result на уровень выше (относительно текущей рабочей директории)
            Path currentPath = Paths.get("").toAbsolutePath();
            Path resultPath = currentPath.getParent().resolve("result");
            String resultDirPath = resultPath.toString();
            
            // Создаем папку если нет
            java.io.File resultDir = new java.io.File(resultDirPath);
            if (!resultDir.exists()) {
                resultDir.mkdirs();
                System.out.println("Создана папка: " + resultDirPath);
            }
            
            String filePath = resultDirPath + "/forecast.txt";
            FileWriter fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            printWriter.println("Прогноз погоды для Нижнего Новгорода (координаты: 56°N, 44°E)");
            printWriter.println("================================================================================\n");
            printWriter.printf("%-4s %-20s %-12s %-12s\n", "№", "Дата/время", "Температура,°C", "Осадки, мм");
            printWriter.println("--------------------------------------------------------------------------------");
            
            for (int i = 0; i < timeArray.size(); i++) {
                String time = (String) timeArray.get(i);
                String formattedTime = time.replace("T", " ");
                double temperature = (double) temperatureArray.get(i);
                double rain = (double) rainArray.get(i);
                
                System.out.printf("│ %-2d │ %-19s │ %-11.1f │ %-11.2f │\n", 
                                 (i+1), formattedTime, temperature, rain);
                
                printWriter.printf("%-4d %-20s %-12.1f %-12.2f\n", 
                                 (i+1), formattedTime, temperature, rain);
                
                if ((i+1) % 6 == 0 && i != timeArray.size()-1) {
                    System.out.println("├────┼─────────────────────┼─────────────┼─────────────┤");
                }
            }
            
            System.out.println("└────┴─────────────────────┴─────────────┴─────────────┘");
            
            printWriter.println("\n================================================================================");
            printWriter.println("* Осадки: 0.00 мм - без осадков, >0.00 мм - возможны осадки");
            
            printWriter.close();
            fileWriter.close();
            
            System.out.println("\n✓ Прогноз погоды сохранен в файл: " + filePath);
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("Ошибка при получении прогноза погоды: " + e.toString());
        } finally {
            webDriver.quit();
        }
    }
}