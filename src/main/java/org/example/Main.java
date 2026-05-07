package org.example;

import java.io.File;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "input.txt";
    private static DatabaseManager dbManager; // Поле для роботи з БД

    public static void main(String[] args) {
        // 1. Перевірка аргументів командного рядка
        if (args.length == 0) {
            System.out.println("Помилка: вкажіть шлях до файлу конфігурації БД (наприклад, db.properties) в аргументах!");
            return;
        }

        // 2. Ініціалізація DatabaseManager через файл конфігурації
        try {
            dbManager = new DatabaseManager(args[0]);
            System.out.println("Конфігурація БД завантажена з файлу: " + args[0]);
        } catch (Exception e) {
            System.err.println("Критична помилка ініціалізації БД: " + e.getMessage());
            return;
        }

        // Налаштування виводу та вводу для підтримки UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        
        // Ініціалізація Магазину (Клас-контейнер з ПР №11)
        Store myStore = new Store("Магазин Одягу з JDBC");

        // Завантаження початкових даних з текстового файлу (ПР №9-11)
        loadFromFile(FILE_NAME, myStore);

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ (" + myStore.getName() + ") ---");
            System.out.println("1. Пошук товару");
            System.out.println("2. Додати товар в магазин (збереження в Колекцію + БД)");
            System.out.println("3. Вивести весь асортимент");
            System.out.println("4. Завершити роботу програми");
            System.out.print("Оберіть дію: ");

            String mainChoice = scanner.nextLine();

            switch (mainChoice) {
                case "1":
                    handleSearchMenu(scanner, myStore);
                    break;
                case "2":
                    handleCreationMenu(scanner, myStore);
                    break;
                case "3":
                    System.out.println("\nАсортимент магазину (Унікальних позицій: " + myStore.getInventory().size() + "):");
                    printList(myStore.getInventory());
                    break;
                case "4":
                    saveToFile(FILE_NAME, myStore);
                    System.out.println("Програму успішно завершено!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }

    private static void handleSearchMenu(Scanner scanner, Store store) {
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. Знайти за розміром");
        System.out.println("2. Знайти за брендом");
        System.out.println("3. Знайти за діапазоном ціни");
        System.out.println("4. Назад");
        System.out.print("Оберіть критерій: ");

        String searchChoice = scanner.nextLine();
        if (searchChoice.equals("4")) return;

        ArrayList<StoreItem> results = new ArrayList<>();

        try {
            switch (searchChoice) {
                case "1":
                    System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
                    Size targetSize = Size.valueOf(scanner.nextLine().toUpperCase().trim());
                    results = store.searchBySize(targetSize);
                    break;
                case "2":
                    System.out.print("Введіть бренд для пошуку: ");
                    String targetBrand = scanner.nextLine();
                    results = store.searchByBrand(targetBrand);
                    break;
                case "3":
                    System.out.print("Введіть мінімальну ціну: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print("Введіть максимальну ціну: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine();
                    if (minPrice > maxPrice) {
                        System.out.println("Помилка: мінімальна ціна не може бути більшою за максимальну.");
                        return;
                    }
                    results = store.searchByPriceRange(minPrice, maxPrice);
                    break;
                default:
                    System.out.println("Некоректний вибір.");
                    return;
            }
            System.out.println("\nРезультати пошуку:");
            printList(results);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка вводу: Такого розміру не існує.");
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: Некоректний формат числа.");
            scanner.nextLine();
        }
    }

    private static void handleCreationMenu(Scanner scanner, Store store) {
        System.out.println("\n--- ДОДАВАННЯ ТОВАРУ ---");
        System.out.println("1. Штани (Pants)");
        System.out.println("2. Сорочка (Shirts)");
        System.out.println("3. Верхній одяг (Outerwear)");
        System.out.println("4. Сукня (Dress)");
        System.out.println("5. Базовий одяг (Clothes)");
        System.out.println("6. Назад");
        System.out.print("Оберіть тип об'єкта: ");

        String createChoice = scanner.nextLine();
        if (createChoice.equals("6") || !createChoice.matches("[1-5]")) return;

        try {
            System.out.print("Введіть тип: ");
            String type = scanner.nextLine();
            System.out.print("Введіть бренд: ");
            String brand = scanner.nextLine();
            System.out.print("Введіть розмір (XS, S, M, L, XL, XXL): ");
            Size size = Size.valueOf(scanner.nextLine().toUpperCase().trim());
            System.out.print("Введіть ціну: ");
            double price = scanner.nextDouble();
            System.out.print("Введіть КІЛЬКІСТЬ товару (шт.): ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Clothes newClothes = null;

            switch (createChoice) {
                case "1":
                    System.out.print("Чи є ремінь? (true/false): ");
                    newClothes = new Pants(type, brand, size, price, scanner.nextBoolean());
                    break;
                case "2":
                    System.out.print("Короткі рукави? (true/false): ");
                    newClothes = new Shirts(type, brand, size, price, scanner.nextBoolean());
                    break;
                case "3":
                    System.out.print("Водонепроникний? (true/false): ");
                    newClothes = new Outerwear(type, brand, size, price, scanner.nextBoolean());
                    break;
                case "4":
                    System.out.print("Вечірня сукня? (true/false): ");
                    newClothes = new Dress(type, brand, size, price, scanner.nextBoolean());
                    break;
                case "5":
                    newClothes = new Clothes(type, brand, size, price);
                    break;
            }
            if (scanner.hasNextLine()) scanner.nextLine();
            
            if (newClothes != null) {
                // 1. Додавання в локальну колекцію (ПР №11)
                store.addNewClothes(newClothes, quantity);
                
                // 2. Збереження в Базу Даних через JDBC (ПР №12)
                if (dbManager != null) {
                    dbManager.saveClothes(newClothes);
                }
                
                System.out.println("Товар успішно додано в Магазин та базу даних!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Помилка вводу: некоректний формат!");
            scanner.nextLine();
        }
    }

    private static void printList(ArrayList<StoreItem> list) {
        if (list.isEmpty()) {
            System.out.println("Нічого не знайдено / Колекція порожня.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i).toString());
            }
        }
    }

    private static void loadFromFile(String filename, Store store) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
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
                    
                    int quantity = Integer.parseInt(parts[parts.length - 1]);
                    Clothes cl = null;

                    switch (className) {
                        case "Clothes": cl = new Clothes(type, brand, size, price); break;
                        case "Pants": cl = new Pants(type, brand, size, price, Boolean.parseBoolean(parts[5])); break;
                        case "Shirts": cl = new Shirts(type, brand, size, price, Boolean.parseBoolean(parts[5])); break;
                        case "Outerwear": cl = new Outerwear(type, brand, size, price, Boolean.parseBoolean(parts[5])); break;
                        case "Dress": cl = new Dress(type, brand, size, price, Boolean.parseBoolean(parts[5])); break;
                    }
                    if (cl != null) {
                        store.addNewClothes(cl, quantity);
                    }
                } catch (Exception e) { /* Пропускаємо некоректні рядки */ }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static void saveToFile(String filename, Store store) {
        try (PrintWriter writer = new PrintWriter(new File(filename), StandardCharsets.UTF_8.name())) {
            ArrayList<StoreItem> items = store.getInventory();
            for (int i = 0; i < items.size(); i++) {
                writer.println(items.get(i).getClothes().toDataString() + ";" + items.get(i).getQuantity());
            }
            System.out.println("\n[!] Дані успішно синхронізовано з файлом " + filename);
        } catch (Exception e) {
            System.out.println("\n[!] Помилка збереження у файл: " + e.getMessage());
        }
    }
}