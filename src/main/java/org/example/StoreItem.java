package org.example;

/**
 * Обгортка, яка також реалізує Comparable для сортування асортименту магазину.
 */
public class StoreItem implements Comparable<StoreItem> {
    private Clothes clothes;
    private int quantity;

    public StoreItem(Clothes clothes, int quantity) {
        this.clothes = clothes;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(StoreItem other) {
        // Використовуємо логіку порівняння самого одягу
        return this.clothes.compareTo(other.getClothes());
    }

    public Clothes getClothes() { return clothes; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int amount) { this.quantity += amount; }

    @Override
    public String toString() {
        return clothes.toString() + " | К-сть: " + quantity;
    }
}