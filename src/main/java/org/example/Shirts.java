package org.example;

/**
 * Похідний клас "Сорочки", успадкований від Clothes.
 */
public class Shirts extends Clothes {
    private boolean shortSleeves; // Чи короткі рукави

    public Shirts(String type, String brand, Size size, double price, boolean shortSleeves) {
        super(type, brand, size, price); // Виклик конструктора базового класу
        this.shortSleeves = shortSleeves;
    }

    public boolean isShortSleeves() { return shortSleeves; }
    public void setShortSleeves(boolean shortSleeves) { this.shortSleeves = shortSleeves; }

    @Override
    public String toDataString() {
        return "Shirts;" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + shortSleeves;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Shirts shirts = (Shirts) o;
        return shortSleeves == shirts.shortSleeves;
    }
}