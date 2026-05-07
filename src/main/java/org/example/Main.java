package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {}

        Scanner scanner = new Scanner(System.in, "UTF-8");
        Store store = new Store("Мій Абстрактний Магазин");

        loadFromFile(FILE_NAME, store);

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Пошук товару");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Вивести весь асортимент");
            System.out.println("4. Вивести ВІДСОРТОВАНИЙ асортимент (Comparable)");
            System.out.println("5. Завершити роботу");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": handleSearch(scanner, store); break;
                case "2": handleCreation(scanner, store); break;
                case "3": printList(store.getInventory()); break;
                case "4": 
                    ArrayList<StoreItem> sorted = new ArrayList<>(store.getInventory());
                    Collections.sort(sorted);
                    System.out.println("\n--- ВІДСОРТОВАНО (Бренд -> Ціна) ---");
                    printList(sorted);
                    break;
                case "5":
                    saveToFile(FILE_NAME, store);
                    System.out.println("Вихід...");
                    return;
                default: System.out.println("Некоректний ввід.");
            }
        }
    }

    private static void handleCreation(Scanner scanner, Store store) {
        System.out.println("\nТип: 1.Штани 2.Сорочка 3.Верхній одяг 4.Сукня 5.Назад");
        String c = scanner.nextLine();
        if (c.equals("5")) return;

        try {
            System.out.print("Назва моделі: "); String type = scanner.nextLine();
            System.out.print("Бренд: "); String brand = scanner.nextLine();
            System.out.print("Розмір (XS-XXL): "); Size sz = Size.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Ціна: "); double pr = Double.parseDouble(scanner.nextLine());
            System.out.print("Кількість: "); int q = Integer.parseInt(scanner.nextLine());

            Clothes cl = null;
            switch (c) {
                case "1": System.out.print("Ремінь (true/false): "); cl = new Pants(type, brand, sz, pr, scanner.nextBoolean()); break;
                case "2": System.out.print("Кор. рукав (true/false): "); cl = new Shirts(type, brand, sz, pr, scanner.nextBoolean()); break;
                case "3": System.out.print("Водостійкість (true/false): "); cl = new Outerwear(type, brand, sz, pr, scanner.nextBoolean()); break;
                case "4": System.out.print("Вечірня (true/false): "); cl = new Dress(type, brand, sz, pr, scanner.nextBoolean()); break;
            }
            if (scanner.hasNextLine() && !c.equals("5")) scanner.nextLine();
            if (cl != null) store.addNewClothes(cl, q);
        } catch (Exception e) { System.out.println("Помилка: " + e.getMessage()); }
    }

    private static void handleSearch(Scanner scanner, Store store) {
        System.out.println("1.За розміром 2.За брендом");
        String s = scanner.nextLine();
        if (s.equals("1")) {
            System.out.print("Розмір: ");
            printList(store.searchBySize(Size.valueOf(scanner.nextLine().toUpperCase())));
        } else {
            System.out.print("Бренд: ");
            printList(store.searchByBrand(scanner.nextLine()));
        }
    }

    private static void printList(ArrayList<StoreItem> list) {
        if (list.isEmpty()) System.out.println("Список порожній.");
        else for (StoreItem i : list) System.out.println(i);
    }

    private static void loadFromFile(String f, Store s) {
    File file = new File(f);
    if (!file.exists()) {
        System.out.println("[DEBUG] Файл " + f + " не знайдено за шляхом: " + file.getAbsolutePath());
        return;
    }
    try (Scanner fs = new Scanner(file, "UTF-8")) {
        int count = 0;
        while (fs.hasNextLine()) {
            String line = fs.nextLine();
            if (line.trim().isEmpty()) continue;
            String[] p = line.split(";");
            try {
                Clothes cl = null;
                Size sz = Size.valueOf(p[3].toUpperCase());
                double pr = Double.parseDouble(p[4]);
                int q = Integer.parseInt(p[p.length - 1]); // Останнє число - кількість

                if (p[0].equals("Pants")) cl = new Pants(p[1], p[2], sz, pr, Boolean.parseBoolean(p[5]));
                else if (p[0].equals("Shirts")) cl = new Shirts(p[1], p[2], sz, pr, Boolean.parseBoolean(p[5]));
                else if (p[0].equals("Outerwear")) cl = new Outerwear(p[1], p[2], sz, pr, Boolean.parseBoolean(p[5]));
                else if (p[0].equals("Dress")) cl = new Dress(p[1], p[2], sz, pr, Boolean.parseBoolean(p[5]));
                
                if (cl != null) {
                    s.addNewClothes(cl, q);
                    count++;
                }
            } catch (Exception e) {
                System.out.println("[DEBUG] Помилка в рядку: " + line + " -> " + e.getMessage());
            }
        }
        System.out.println("[DEBUG] Завантажено об'єктів: " + count);
    } catch (Exception e) {
        System.out.println("[DEBUG] Критична помилка читання: " + e.getMessage());
    }
}
    private static void saveToFile(String f, Store s) {
        try (PrintWriter w = new PrintWriter(new File(f), "UTF-8")) {
            for (StoreItem i : s.getInventory()) w.println(i.getClothes().toDataString() + ";" + i.getQuantity());
        } catch (Exception e) {}
    }
}