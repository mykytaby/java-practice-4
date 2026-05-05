package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("=== Практична робота №6. Драйвер програми ===");
        System.out.print("Введіть розмір шафи: ");
        int capacity = 0;
        try {
            capacity = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Помилка вводу. Завершення.");
            return;
        }
        scanner.nextLine(); 

        Wardrobe wardrobe;
        try {
            wardrobe = new Wardrobe(capacity);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Додати новий одяг");
            System.out.println("2. Скопіювати останній одяг (Тест конструктора копіювання)");
            System.out.println("3. Вивести всі речі з шафи");
            System.out.println("4. Показати загальну статистику (Тест статичного поля)");
            System.out.println("5. Вийти");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Введіть тип: ");
                        String type = scanner.nextLine();
                        System.out.print("Введіть бренд: ");
                        String brand = scanner.nextLine();
                        
                        System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
                        Size size = Size.valueOf(scanner.nextLine().toUpperCase().trim());
                        
                        System.out.print("Введіть ціну: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); 

                        Clothes clothes = new Clothes(type, brand, size, price);
                        wardrobe.addClothes(clothes);
                        System.out.println("Одяг успішно додано!");
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Помилка: Некоректний формат числа!");
                        scanner.nextLine();
                    }
                    break;
                case "2":
                    Clothes last = wardrobe.getLastItem();
                    if (last == null) {
                        System.out.println("Спочатку додайте хоча б одну річ!");
                    } else {
                        try {
                            Clothes copy = new Clothes(last);
                            wardrobe.addClothes(copy);
                            System.out.println("Успішно створено та додано копію: " + copy.getType());
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "3":
                    System.out.println("\nВаш гардероб:");
                    wardrobe.displayWardrobe();
                    break;
                case "4":
                    System.out.println("\nСтатистика системи:");
                    System.out.println("Всього створено об'єктів Clothes за час роботи: " + Clothes.getTotalClothesCreated());
                    break;
                case "5":
                    System.out.println("Завершення роботи.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома команда.");
            }
        }
    }
}