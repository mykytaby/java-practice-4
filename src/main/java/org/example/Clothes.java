package org.example;

import java.util.Objects;
import java.util.UUID;

public abstract class Clothes implements Identifiable, Comparable<Clothes> {
    private final UUID uuid;
    private String type;
    private String brand;
    private Size size;
    private double price;

    // Конструктор для нових об'єктів
    public Clothes(String type, String brand, Size size, double price) {
        this(UUID.randomUUID(), type, brand, size, price);
    }

    // Конструктор для відновлення з файлу
    public Clothes(UUID uuid, String type, String brand, Size size, double price) {
        this.uuid = uuid;
        this.type = type;
        this.brand = brand;
        this.size = size;
        this.price = price;
    }

    @Override
    public UUID getUuid() { return uuid; }

    @Override
    public int compareTo(Clothes other) {
        int brandCompare = this.brand.compareToIgnoreCase(other.brand);
        return (brandCompare != 0) ? brandCompare : Double.compare(this.price, other.price);
    }

    public String getType() { return type; }
    public String getBrand() { return brand; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }

    public abstract String toDataString();

    @Override
    public String toString() {
        return String.format("[%s] %-10s %-12s | Ціна: %8.2f | ID: %s", 
                this.getClass().getSimpleName(), brand, type, price, uuid.toString().substring(0, 8));
    }

    public String getFullDetails() {
        return String.format("Клас: %s\nТип: %s\nБренд: %s\nРозмір: %s\nЦіна: %.2f\nUUID: %s", 
                this.getClass().getSimpleName(), type, brand, size, price, uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(uuid, clothes.uuid);
    }

    @Override
    public int hashCode() { return Objects.hash(uuid); }
}