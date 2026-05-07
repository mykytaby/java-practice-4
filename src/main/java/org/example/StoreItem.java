package org.example;

public class StoreItem implements Comparable<StoreItem> {
    private Clothes clothes;
    private int quantity;

    public StoreItem(Clothes clothes, int quantity) {
        this.clothes = clothes;
        this.quantity = quantity;
    }

    public Clothes getClothes() { return clothes; }
    public int getQuantity() { return quantity; }
    
    public void addQuantity(int amount) { this.quantity += amount; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public int compareTo(StoreItem other) {
        return this.clothes.compareTo(other.getClothes());
    }

    // Перевизначення equals для коректного пошуку (видалення/оновлення) за UUID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem storeItem = (StoreItem) o;
        return this.clothes.getUuid().equals(storeItem.clothes.getUuid());
    }

    @Override
    public String toString() {
        return clothes.toString() + " | К-сть: " + quantity + " шт.";
    }
}