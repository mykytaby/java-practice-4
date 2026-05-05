package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        // Використовуємо ArrayList замість масиву та класу Wardrobe
        ArrayList<Clothes> wardrobe = new ArrayList<>();

        System.out.println("=== Практична робота №7. Колекції та Поліморфізм ===");

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Додати Штани (Pants)");
            System.out.println("2. Додати Сорочку/Футболку (Shirts)");
            System.out.println("3. Додати інший загальний одяг (Clothes)");
            System.out.println("4. Вивести весь гардероб (Демонстрація поліморфізму)");
            System.out.println("5. Вийти");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                case "2":
                case "3":
                    try {
                        System.out.print("Введіть тип (наприклад Джинси, Поло, Куртка): ");
                        String type = scanner.nextLine();
                        System.out.print("Введіть бренд: ");
                        String brand = scanner.nextLine();
                        System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
                        Size size = Size.valueOf(scanner.nextLine().toUpperCase().trim());
                        System.out.print("Введіть ціну: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // очищення буфера

                        if (choice.equals("1")) {
                            System.out.print("Чи є ремінь в комплекті? (true/false): ");
                            boolean hasBelt = scanner.nextBoolean();
                            scanner.nextLine();
                            wardrobe.add(new Pants(type, brand, size, price, hasBelt));
                            System.out.println("Штани додано!");
                        } else if (choice.equals("2")) {
                            System.out.print("Це сорочка з коротким рукавом? (true/false): ");
                            boolean shortSleeves = scanner.nextBoolean();
                            scanner.nextLine();
                            wardrobe.add(new Shirts(type, brand, size, price, shortSleeves));
                            System.out.println("Сорочку додано!");
                        } else {
                            wardrobe.add(new Clothes(type, brand, size, price));
                            System.out.println("Одяг додано!");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка валідації: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Помилка: Некоректний формат числа або логічного значення!");
                        scanner.nextLine();
                    }
                    break;

                case "4":
                    System.out.println("\nВаш гардероб (Розмір колекції: " + wardrobe.size() + "):");
                    if (wardrobe.isEmpty()) {
                        System.out.println("Шафа порожня.");
                    } else {
                        // Демонстрація поліморфізму: 
                        // об'єкти Pants та Shirts викликають свої перевизначені методи toString(),
                        // хоча зберігаються у списку типу Clothes.
                        for (int i = 0; i < wardrobe.size(); i++) {
                            Clothes item = wardrobe.get(i);
                            System.out.println((i + 1) + ". " + item.toString());
                        }
                    }
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