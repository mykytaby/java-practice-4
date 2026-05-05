package org.example;

/**
 * Похідний клас "Сукня", успадкований від Clothes.
 */
public class Dress extends Clothes {
    private boolean isEvening; // Чи є вечірньою сукнею

    public Dress(String type, String brand, Size size, double price, boolean isEvening) {
        super(type, brand, size, price);
        this.isEvening = isEvening;
    }

    public boolean isEvening() { return isEvening; }
    public void setEvening(boolean evening) { this.isEvening = evening; }

    @Override
    public String toString() {
        return "Dress{" +
                "type='" + getType() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", isEvening=" + isEvening +
                '}';
    }

    @Override
    public String toDataString() {
        return "Dress;" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + isEvening;
    }
}