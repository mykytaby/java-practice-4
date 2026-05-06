package org.example;

/**
 * Похідний клас "Штани", успадкований від Clothes.
 */
public class Pants extends Clothes {
    private boolean hasBelt; // Чи є ремінь у комплекті

    public Pants(String type, String brand, Size size, double price, boolean hasBelt) {
        super(type, brand, size, price); // Виклик конструктора базового класу
        this.hasBelt = hasBelt;
    }

    public boolean isHasBelt() { return hasBelt; }
    public void setHasBelt(boolean hasBelt) { this.hasBelt = hasBelt; }

    @Override
    public String toString() {
        // Перевизначаємо toString для демонстрації поліморфізму
        return "Pants{" +
                "brand='" + getBrand() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", hasBelt=" + hasBelt +
                '}';
    }

    @Override
    public String toDataString() {
        return "Pants;" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + hasBelt;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Pants pants = (Pants) o;
        return hasBelt == pants.hasBelt;
    }
}