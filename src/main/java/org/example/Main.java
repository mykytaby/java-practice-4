package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        // Налаштування UTF-8 для виводу в консоль
        try { System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name())); } catch (Exception e) {}

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        Store store = new Store("Магазин Одягу (CRUD + Exceptions)");

        loadFromFile(FILE_NAME, store);

        while (true) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Пошук товару");
            System.out.println("2. Додати новий товар");
            System.out.println("3. Модифікувати товар (Update)");
            System.out.println("4. Видалити товар (Delete)");
            System.out.println("5. Вивести весь асортимент");
            System.out.println("6. Сортувати асортимент (Lambda)");
            System.out.println("7. Завершити роботу");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": handleSearch(scanner, store); break;
                case "2": handleCreation(scanner, store); break;
                case "3": handleUpdate(scanner, store); break;
                case "4": handleDelete(scanner, store); break;
                case "5": printList(store.getInventory()); break;
                case "6": handleSortMenu(scanner, store); break;
                case "7":
                    saveToFile(FILE_NAME, store);
                    System.out.println("Дані збережено. Вихід...");
                    scanner.close();
                    return;
                default: System.out.println("Некоректний ввід.");
            }
        }
    }

    private static void handleUpdate(Scanner scanner, Store store) {
        if (store.getInventory().isEmpty()) {
            System.out.println("Колекція порожня. Немає що модифікувати.");
            return;
        }

        System.out.print("\nВведіть UUID товару для модифікації: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine().trim());
            
            // Якщо об'єкта немає, store.findByUuid() кине ObjectNotFoundException
            StoreItem existingItem = store.findByUuid(id); 

            System.out.println("Знайдено: " + existingItem.toString());
            System.out.println("Що бажаєте змінити?");
            System.out.println("1. Змінити ціну");
            System.out.println("2. Змінити кількість на складі");
            System.out.print("Вибір: ");
            String updateChoice = scanner.nextLine();

            Clothes clothes = existingItem.getClothes();
            int newQuantity = existingItem.getQuantity();

            if (updateChoice.equals("1")) {
                System.out.print("Введіть нову ціну: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                
                // Якщо ціна <= 0, clothes.setPrice() кине InvalidClothesDataException
                clothes.setPrice(newPrice); 
                
            } else if (updateChoice.equals("2")) {
                System.out.print("Введіть нову кількість: ");
                newQuantity = Integer.parseInt(scanner.nextLine());
            } else {
                System.out.println("Операцію скасовано.");
                return;
            }

            StoreItem newItem = new StoreItem(clothes, newQuantity);
            if (store.update(existingItem, newItem)) {
                System.out.println("[OK] Товар успішно модифіковано!");
            }

        // --- БЛОК ПЕРЕХОПЛЕННЯ ВЛАСНИХ ТА СИСТЕМНИХ ВИНЯТКІВ ---
        } catch (ObjectNotFoundException e) {
            System.out.println("[ПОМИЛКА] " + e.getMessage());
        } catch (InvalidClothesDataException e) {
            System.out.println("[ПОМИЛКА ДАНИХ] " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[ПОМИЛКА ВВОДУ] Некоректний формат UUID або числа!");
        }
    }

    private static void handleDelete(Scanner scanner, Store store) {
        if (store.getInventory().isEmpty()) {
            System.out.println("Колекція порожня. Немає що видаляти.");
            return;
        }

        System.out.print("\nВведіть UUID товару для видалення: ");
        try {
            UUID id = UUID.fromString(scanner.nextLine().trim());
            
            // Якщо об'єкта немає, store.findByUuid() кине ObjectNotFoundException
            StoreItem existingItem = store.findByUuid(id); 

            System.out.println("Знайдено: " + existingItem.toString());
            System.out.print("Ви дійсно хочете видалити цей товар? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("т")) {
                store.delete(existingItem);
                System.out.println("[OK] Товар успішно видалено!");
            } else {
                System.out.println("Видалення скасовано.");
            }
            
        // --- БЛОК ПЕРЕХОПЛЕННЯ ВЛАСНИХ ВИНЯТКІВ ---
        } catch (ObjectNotFoundException e) {
            System.out.println("[ПОМИЛКА] " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[ПОМИЛКА ВВОДУ] Некоректний формат UUID!");
        }
    }

    private static void handleSortMenu(Scanner scanner, Store store) {
        if (store.getInventory().isEmpty()) { System.out.println("Список порожній."); return; }
        System.out.println("\n1. За ціною | 2. За кількістю | 3. За назвою");
        String choice = scanner.nextLine();
        ArrayList<StoreItem> sorted = new ArrayList<>(store.getInventory());
        if (choice.equals("1")) Collections.sort(sorted, (o1, o2) -> Double.compare(o1.getClothes().getPrice(), o2.getClothes().getPrice()));
        else if (choice.equals("2")) Collections.sort(sorted, (o1, o2) -> Integer.compare(o2.getQuantity(), o1.getQuantity()));
        else if (choice.equals("3")) Collections.sort(sorted, (o1, o2) -> o1.getClothes().getType().compareToIgnoreCase(o2.getClothes().getType()));
        else return;
        printList(sorted);
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
            if (c.equals("1")) { System.out.print("Ремінь (true/false): "); cl = new Pants(type, brand, sz, pr, scanner.nextBoolean()); }
            else if (c.equals("2")) { System.out.print("Кор. рукав (true/false): "); cl = new Shirts(type, brand, sz, pr, scanner.nextBoolean()); }
            else if (c.equals("3")) { System.out.print("Водостійкість (true/false): "); cl = new Outerwear(type, brand, sz, pr, scanner.nextBoolean()); }
            else if (c.equals("4")) { System.out.print("Вечірня (true/false): "); cl = new Dress(type, brand, sz, pr, scanner.nextBoolean()); }

            if (scanner.hasNextLine()) scanner.nextLine();
            if (cl != null) {
                store.addNewClothes(cl, q);
                System.out.println("[OK] Товар успішно додано!");
            }
            
        // --- БЛОК ПЕРЕХОПЛЕННЯ ВЛАСНИХ ВИНЯТКІВ ---
        } catch (InvalidClothesDataException e) {
            System.out.println("[ПОМИЛКА ДАНИХ] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ПОМИЛКА] " + e.getMessage());
        }
    }

    private static void handleSearch(Scanner scanner, Store store) {
        System.out.println("1.За розміром 2.За брендом 3.За UUID");
        String s = scanner.nextLine();
        if (s.equals("1")) { System.out.print("Розмір: "); printList(store.searchBySize(Size.valueOf(scanner.nextLine().toUpperCase()))); }
        else if (s.equals("2")) { System.out.print("Бренд: "); printList(store.searchByBrand(scanner.nextLine())); }
        else if (s.equals("3")) {
            System.out.print("UUID: ");
            try {
                UUID id = UUID.fromString(scanner.nextLine().trim());
                StoreItem res = store.findByUuid(id); // Кине ObjectNotFoundException, якщо не знайде
                System.out.println("Знайдено:\n" + res.getClothes().getFullDetails());
                
            // --- БЛОК ПЕРЕХОПЛЕННЯ ВЛАСНИХ ВИНЯТКІВ ---
            } catch (ObjectNotFoundException e) {
                System.out.println("[ПОМИЛКА ПОШУКУ] " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[ПОМИЛКА ВВОДУ] Некоректний формат UUID!");
            }
        }
    }

    private static void printList(ArrayList<StoreItem> list) {
        if (list.isEmpty()) System.out.println("Список порожній.");
        else for (StoreItem i : list) System.out.println(i);
    }

    private static void loadFromFile(String f, Store s) {
        File file = new File(f);
        if (!file.exists()) return;
        try (Scanner fs = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";");
                try {
                    UUID id = UUID.fromString(p[1]);
                    Size sz = Size.valueOf(p[4]);
                    double pr = Double.parseDouble(p[5]);
                    int q = Integer.parseInt(p[p.length-1]);
                    Clothes cl = null;
                    if (p[0].equals("Pants")) cl = new Pants(id, p[2], p[3], sz, pr, Boolean.parseBoolean(p[6]));
                    else if (p[0].equals("Shirts")) cl = new Shirts(id, p[2], p[3], sz, pr, Boolean.parseBoolean(p[6]));
                    else if (p[0].equals("Outerwear")) cl = new Outerwear(id, p[2], p[3], sz, pr, Boolean.parseBoolean(p[6]));
                    else if (p[0].equals("Dress")) cl = new Dress(id, p[2], p[3], sz, pr, Boolean.parseBoolean(p[6]));
                    if (cl != null) s.addNewClothes(cl, q);
                } catch (Exception e) { /* Ігноруємо биті рядки */ }
            }
        } catch (Exception e) {}
    }

    private static void saveToFile(String f, Store s) {
        try (PrintWriter w = new PrintWriter(new File(f), StandardCharsets.UTF_8.name())) {
            for (StoreItem i : s.getInventory()) w.println(i.getClothes().toDataString() + ";" + i.getQuantity());
        } catch (Exception e) {}
    }
}