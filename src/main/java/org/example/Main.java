package org.example;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        ArrayList<Clothes> wardrobe = new ArrayList<>();

        System.out.println("=== Практична робота №9. Робота з файлами ===");
        
        // Зчитуємо дані з файлу при запуску
        loadFromFile(FILE_NAME, wardrobe);

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу програми (і зберегти дані)");
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
                    // Зберігаємо дані перед виходом
                    saveToFile(FILE_NAME, wardrobe);
                    System.out.println("Програму успішно завершено!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }

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
        
        if (createChoice.equals("6")) return;

        if (!createChoice.matches("[1-5]")) {
            System.out.println("Некоректний вибір.");
            return;
        }

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

            switch (createChoice) {
                case "1":
                    System.out.print("Чи є ремінь? (true/false): ");
                    wardrobe.add(new Pants(type, brand, size, price, scanner.nextBoolean()));
                    System.out.println("Штани додано!");
                    break;
                case "2":
                    System.out.print("Короткі рукави? (true/false): ");
                    wardrobe.add(new Shirts(type, brand, size, price, scanner.nextBoolean()));
                    System.out.println("Сорочку додано!");
                    break;
                case "3":
                    System.out.print("Водонепроникний? (true/false): ");
                    wardrobe.add(new Outerwear(type, brand, size, price, scanner.nextBoolean()));
                    System.out.println("Верхній одяг додано!");
                    break;
                case "4":
                    System.out.print("Вечірня сукня? (true/false): ");
                    wardrobe.add(new Dress(type, brand, size, price, scanner.nextBoolean()));
                    System.out.println("Сукню додано!");
                    break;
                case "5":
                    wardrobe.add(new Clothes(type, brand, size, price));
                    System.out.println("Базовий одяг додано!");
                    break;
            }
            if (scanner.hasNextLine()) scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу!");
            scanner.nextLine();
        }
    }

    /**
     * Метод для зчитування даних з файлу у колекцію.
     */
    private static void loadFromFile(String filename, ArrayList<Clothes> wardrobe) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл " + filename + " не знайдено. Буде створено новий при збереженні.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            int lineCount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                try {
                    String className = parts[0];
                    String type = parts[1];
                    String brand = parts[2];
                    Size size = Size.valueOf(parts[3]);
                    double price = Double.parseDouble(parts[4]);

                    switch (className) {
                        case "Clothes":
                            wardrobe.add(new Clothes(type, brand, size, price));
                            break;
                        case "Pants":
                            wardrobe.add(new Pants(type, brand, size, price, Boolean.parseBoolean(parts[5])));
                            break;
                        case "Shirts":
                            wardrobe.add(new Shirts(type, brand, size, price, Boolean.parseBoolean(parts[5])));
                            break;
                        case "Outerwear":
                            wardrobe.add(new Outerwear(type, brand, size, price, Boolean.parseBoolean(parts[5])));
                            break;
                        case "Dress":
                            wardrobe.add(new Dress(type, brand, size, price, Boolean.parseBoolean(parts[5])));
                            break;
                        default:
                            System.out.println("Невідомий клас у файлі: " + className);
                    }
                    lineCount++;
                } catch (Exception e) {
                    System.out.println("Помилка парсингу рядка: " + line);
                }
            }
            System.out.println("Успішно завантажено об'єктів: " + lineCount);
        } catch (Exception e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    /**
     * Метод для запису колекції у файл.
     */
    private static void saveToFile(String filename, ArrayList<Clothes> wardrobe) {
        try (PrintWriter writer = new PrintWriter(new File(filename), StandardCharsets.UTF_8.name())) {
            for (int i = 0; i < wardrobe.size(); i++) {
                writer.println(wardrobe.get(i).toDataString());
            }
            System.out.println("\n[!] Всі дані успішно збережено у файл: " + filename);
        } catch (Exception e) {
            System.out.println("\n[!] Помилка збереження у файл: " + e.getMessage());
        }
    }
}