package org.example;

import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // Встановлюємо UTF-8 для виводу в консоль
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        // Встановлюємо UTF-8 для сканера (вводу)
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        // Далі твій код без змін...
        System.out.print("Введіть кількість одиниць одягу: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Очищення буфера після nextInt()

        // Створення масиву
        Clothes[] wardrobe = new Clothes[n];

        // Заповнення масиву
        for (int i = 0; i < n; i++) {
            System.out.println("\nВведення даних для одягу #" + (i + 1));
            
            System.out.print("Тип (наприклад, Куртка): ");
            String type = scanner.nextLine();
            
            System.out.print("Бренд: ");
            String brand = scanner.nextLine();
            
            System.out.print("Розмір: ");
            String size = scanner.nextLine();
            
            System.out.print("Ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Очищення буфера

            wardrobe[i] = new Clothes(type, brand, size, price);
        }

        // Виведення інформації про всі створені об'єкти
        System.out.println("\n--- Ваш гардероб (інформація про об'єкти) ---");
        for (Clothes item : wardrobe) {
            System.out.println(item.toString());
        }

        scanner.close();
    }
}