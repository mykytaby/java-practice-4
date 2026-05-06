package org.example;

/**
 * Клас-обгортка для зберігання об'єкта одягу та його кількості в магазині.
 */
public class StoreItem {
    private Clothes clothes;
    private int quantity;

    public StoreItem(Clothes clothes, int quantity) {
        this.clothes = clothes;
        this.quantity = quantity;
    }

    public Clothes getClothes() { return clothes; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int amount) { this.quantity += amount; }

    @Override
    public String toString() {
        return clothes.toString() + " | В наявності: " + quantity + " шт.";
    }
}