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

        // Внутрішня колекція, порожня на початку роботи
        ArrayList<Clothes> wardrobe = new ArrayList<>();

        System.out.println("=== Практична робота №8. Розширена ієрархія ===");

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу програми");
            System.out.print("Оберіть дію: ");

            String mainChoice = scanner.nextLine();

            switch (mainChoice) {
                case "1":
                    handleCreationMenu(scanner, wardrobe);
                    break;
                case "2":
                    System.out.println("\nВаш гардероб (Кількість речей: " + wardrobe.size() + "):");
                    if (wardrobe.isEmpty()) {
                        System.out.println("Колекція порожня.");
                    } else {
                        for (int i = 0; i < wardrobe.size(); i++) {
                            System.out.println((i + 1) + ". " + wardrobe.get(i).toString());
                        }
                    }
                    break;
                case "3":
                    System.out.println("Програму успішно завершено!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }

    // Метод для обробки підменю створення об'єктів
    private static void handleCreationMenu(Scanner scanner, ArrayList<Clothes> wardrobe) {
        System.out.println("\n--- МЕНЮ СТВОРЕННЯ ---");
        System.out.println("1. Штани (Pants)");
        System.out.println("2. Сорочка (Shirts)");
        System.out.println("3. Верхній одяг (Outerwear)");
        System.out.println("4. Сукня (Dress)");
        System.out.println("5. Базовий одяг (Clothes)");
        System.out.println("6. Повернутися до головного меню");
        System.out.print("Оберіть тип об'єкта: ");

        String createChoice = scanner.nextLine();
        
        if (createChoice.equals("6")) {
            return; // Повернення до головного меню
        }

        if (!createChoice.matches("[1-5]")) {
            System.out.println("Некоректний вибір. Повернення до головного меню.");
            return;
        }

        try {
            System.out.print("Введіть тип (наприклад Джинси, Пальто): ");
            String type = scanner.nextLine();
            System.out.print("Введіть бренд: ");
            String brand = scanner.nextLine();
            System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
            Size size = Size.valueOf(scanner.nextLine().toUpperCase().trim());
            System.out.print("Введіть ціну: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // очищення буфера

            switch (createChoice) {
                case "1":
                    System.out.print("Чи є ремінь в комплекті? (true/false): ");
                    boolean hasBelt = scanner.nextBoolean();
                    wardrobe.add(new Pants(type, brand, size, price, hasBelt));
                    System.out.println("Штани успішно додано!");
                    break;
                case "2":
                    System.out.print("Короткі рукави? (true/false): ");
                    boolean shortSleeves = scanner.nextBoolean();
                    wardrobe.add(new Shirts(type, brand, size, price, shortSleeves));
                    System.out.println("Сорочку успішно додано!");
                    break;
                case "3":
                    System.out.print("Одяг водонепроникний? (true/false): ");
                    boolean isWaterproof = scanner.nextBoolean();
                    wardrobe.add(new Outerwear(type, brand, size, price, isWaterproof));
                    System.out.println("Верхній одяг успішно додано!");
                    break;
                case "4":
                    System.out.print("Це вечірня сукня? (true/false): ");
                    boolean isEvening = scanner.nextBoolean();
                    wardrobe.add(new Dress(type, brand, size, price, isEvening));
                    System.out.println("Сукню успішно додано!");
                    break;
                case "5":
                    wardrobe.add(new Clothes(type, brand, size, price));
                    System.out.println("Базовий одяг успішно додано!");
                    break;
            }
            if (scanner.hasNextLine()) scanner.nextLine(); // фінальне очищення
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: Некоректний формат числа або логічного значення!");
            scanner.nextLine();
        }
    }
}