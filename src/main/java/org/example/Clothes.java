package org.example;

import java.util.Objects;

/**
 * Клас, що описує предмет одягу.
 */
public class Clothes {
    // Статичне поле для підрахунку створених об'єктів
    private static int totalClothesCreated = 0;

    private String type;
    private String brand;
    private Size size; // Використовуємо enum замість String
    private double price;

    /**
     * Основний конструктор з параметрами.
     */
    public Clothes(String type, String brand, Size size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
        totalClothesCreated++; // Збільшуємо лічильник при створенні
    }

    /**
     * Конструктор копіювання.
     * @param other об'єкт для копіювання
     */
    public Clothes(Clothes other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null.");
        }
        this.type = other.type;
        this.brand = other.brand;
        this.size = other.size;
        this.price = other.price;
        totalClothesCreated++; // Збільшуємо лічильник при копіюванні
    }

    /**
     * Статичний метод для отримання загальної кількості створених об'єктів.
     */
    public static int getTotalClothesCreated() {
        return totalClothesCreated;
    }

    // Гетери та сетери
    public String getType() { return type; }
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип одягу не може бути порожнім.");
        }
        this.type = type;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд не може бути порожнім.");
        }
        this.brand = brand;
    }

    public Size getSize() { return size; }
    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір не може бути null.");
        }
        this.size = size;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за нуль.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Clothes{type='" + type + "', brand='" + brand + "', size=" + size + ", price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                size == clothes.size &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(brand, clothes.brand);
    }
}