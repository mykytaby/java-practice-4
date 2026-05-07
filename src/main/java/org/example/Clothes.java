package org.example;

import java.util.Objects;

/**
 * Абстрактний батьківський клас.
 * Реалізує Comparable для сортування за брендом та ціною.
 */
public abstract class Clothes implements Comparable<Clothes> {
    private String type;
    private String brand;
    private Size size;
    private double price;

    public Clothes(String type, String brand, Size size, double price) {
        this.type = type;
        this.brand = brand;
        this.size = size;
        this.price = price;
    }

    // Реалізація інтерфейсу Comparable
    @Override
    public int compareTo(Clothes other) {
        // 1. Порівнюємо за брендом (алфавітний порядок, ігноруючи регістр)
        int brandCompare = this.brand.compareToIgnoreCase(other.brand);
        if (brandCompare != 0) {
            return brandCompare;
        }
        // 2. Якщо бренди однакові, порівнюємо за ціною (від меншої до більшої)
        return Double.compare(this.price, other.price);
    }

    public String getType() { return type; }
    public String getBrand() { return brand; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }

    // Абстрактний метод для збереження у файл
    public abstract String toDataString();

    @Override
    public String toString() {
        return String.format("[%s] Бренд: %-10s | Тип: %-12s | Розмір: %-3s | Ціна: %8.2f", 
                this.getClass().getSimpleName(), getBrand(), getType(), getSize(), getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(brand, clothes.brand) &&
                size == clothes.size;
    }
}