package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.print("Введіть максимальну кількість речей у шафі (розмір масиву): ");
        int capacity = 0;
        try {
            capacity = scanner.nextInt();
            if (capacity <= 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Помилка! Кількість має бути додатним числом. Програму завершено.");
            return;
        }
        scanner.nextLine(); // очищення буфера

        Clothes[] wardrobe = new Clothes[capacity];
        int count = 0;

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Додати новий одяг");
            System.out.println("2. Вивести всі речі");
            System.out.println("3. Вийти");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (count >= capacity) {
                        System.out.println("Шафа повна! Неможливо додати більше речей.");
                        break;
                    }
                    try {
                        System.out.print("Введіть тип: ");
                        String type = scanner.nextLine();
                        System.out.print("Введіть бренд: ");
                        String brand = scanner.nextLine();
                        System.out.print("Введіть розмір: ");
                        String size = scanner.nextLine();
                        System.out.print("Введіть ціну: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine(); // очищення буфера

                        wardrobe[count] = new Clothes(type, brand, size, price);
                        count++;
                        System.out.println("Одяг успішно додано!");
                    } catch (InputMismatchException e) {
                        System.out.println("Помилка: Некоректний формат числа (ціна)!");
                        scanner.nextLine(); // очищення зламаного вводу
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка валідації: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("\nВаш гардероб:");
                    if (count == 0) {
                        System.out.println("Шафа порожня.");
                    } else {
                        for (int i = 0; i < count; i++) {
                            System.out.println((i + 1) + ". " + wardrobe[i].toString());
                        }
                    }
                    break;

                case "3":
                    System.out.println("Роботу завершено. До побачення!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
    }
}