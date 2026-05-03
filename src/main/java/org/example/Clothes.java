package org.example;

import java.util.Objects;

/**
 * Клас, що описує предмет одягу.
 * Містить інформацію про тип, бренд, розмір та ціну.
 */
public class Clothes {
    private String type;
    private String brand;
    private String size;
    private double price;

    /**
     * Конструктор з перевіркою параметрів.
     */
    public Clothes(String type, String brand, String size, double price) {
        setType(type);
        setBrand(brand);
        setSize(size);
        setPrice(price);
    }

    public String getType() { return type; }

    /**
     * Встановлює тип одягу.
     * @param type тип (не може бути порожнім)
     * @throws IllegalArgumentException якщо рядок порожній
     */
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

    public String getSize() { return size; }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Розмір не може бути порожнім.");
        }
        this.size = size;
    }

    public double getPrice() { return price; }

    /**
     * Встановлює ціну.
     * @param price ціна (повинна бути > 0)
     * @throws IllegalArgumentException якщо ціна від'ємна або нуль
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна повинна бути більшою за нуль.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Clothes{type='" + type + "', brand='" + brand + "', size='" + size + "', price=" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Double.compare(clothes.price, price) == 0 &&
                Objects.equals(type, clothes.type) &&
                Objects.equals(brand, clothes.brand) &&
                Objects.equals(size, clothes.size);
    }
}