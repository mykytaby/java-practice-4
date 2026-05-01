package org.example;

import java.util.Objects;

public class Clothes {
    private String type;  // тип одягу (наприклад, Футболка, Джинси)
    private String brand; // бренд
    private String size;  // розмір
    private double price; // ціна

    // Конструктор з параметрами
    public Clothes(String type, String brand, String size, double price) {
        this.type = type;
        this.brand = brand;
        this.size = size;
        this.price = price;
    }

    // Гетери та сетери
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Метод toString
    @Override
    public String toString() {
        return "Clothes{" +
                "type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }

    // Метод equals
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