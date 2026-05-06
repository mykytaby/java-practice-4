package org.example;

import java.util.ArrayList;

/**
 * Клас-контейнер, що агрегує колекцію товарів.
 */
public class Store {
    private String name;
    private ArrayList<StoreItem> inventory; // Колекція обгорток

    public Store(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
    }

    public String getName() { return name; }
    public ArrayList<StoreItem> getInventory() { return inventory; }

    /**
     * Додає новий одяг. Якщо такий вже є - збільшує кількість.
     */
    public void addNewClothes(Clothes cl, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більшою за нуль.");
        }
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getClothes().equals(cl)) {
                inventory.get(i).addQuantity(quantity);
                return; // Знайшли дублікат, оновили кількість і вийшли
            }
        }
        // Якщо не знайшли - додаємо як новий запис
        inventory.add(new StoreItem(cl, quantity));
    }

    // --- МЕТОДИ ПОШУКУ (перенесені з Main) ---

    public ArrayList<StoreItem> searchBySize(Size targetSize) {
        ArrayList<StoreItem> found = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getClothes().getSize() == targetSize) {
                found.add(inventory.get(i));
            }
        }
        return found;
    }

    public ArrayList<StoreItem> searchByBrand(String targetBrand) {
        ArrayList<StoreItem> found = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getClothes().getBrand().equalsIgnoreCase(targetBrand.trim())) {
                found.add(inventory.get(i));
            }
        }
        return found;
    }

    public ArrayList<StoreItem> searchByPriceRange(double min, double max) {
        ArrayList<StoreItem> found = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            double price = inventory.get(i).getClothes().getPrice();
            if (price >= min && price <= max) {
                found.add(inventory.get(i));
            }
        }
        return found;
    }
}