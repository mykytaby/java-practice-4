package org.example;

/**
 * Похідний клас "Верхній одяг", успадкований від Clothes.
 */
public class Outerwear extends Clothes {
    private boolean isWaterproof; // Чи є водонепроникним

    public Outerwear(String type, String brand, Size size, double price, boolean isWaterproof) {
        super(type, brand, size, price);
        this.isWaterproof = isWaterproof;
    }

    public boolean isWaterproof() { return isWaterproof; }
    public void setWaterproof(boolean waterproof) { this.isWaterproof = waterproof; }

    @Override
    public String toString() {
        return "Outerwear{" +
                "type='" + getType() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", size=" + getSize() +
                ", price=" + getPrice() +
                ", isWaterproof=" + isWaterproof +
                '}';
    }
}