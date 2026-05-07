package org.example;
import java.util.ArrayList;
import java.util.UUID;

public class Store {
    private String name;
    private ArrayList<StoreItem> inventory = new ArrayList<>();

    public Store(String name) { this.name = name; }
    public ArrayList<StoreItem> getInventory() { return inventory; }

    public void addNewClothes(Clothes cl, int quantity) {
        for (StoreItem item : inventory) {
            if (item.getClothes().equals(cl)) {
                item.addQuantity(quantity);
                return;
            }
        }
        inventory.add(new StoreItem(cl, quantity));
    }

    public StoreItem findByUuid(UUID id) {
        for (StoreItem item : inventory) {
            if (item.getClothes().getUuid().equals(id)) return item;
        }
        return null;
    }

    // --- НОВІ МЕТОДИ ЗГІДНО ЛР 17 ---

    /**
     * Завдання 1: Модифікація об'єкта (update)
     */
    public boolean update(StoreItem existingObject, StoreItem newObject) {
        if (existingObject == null || newObject == null) return false;
        
        int index = inventory.indexOf(existingObject);
        if (index != -1) {
            inventory.set(index, newObject); // Замінюємо старий об'єкт новим
            return true;
        }
        return false;
    }

    /**
     * Завдання 2: Видалення об'єкта (delete)
     */
    public boolean delete(StoreItem existingObject) {
        if (existingObject == null) return false;
        // Метод remove використовує перевизначений equals() у StoreItem
        return inventory.remove(existingObject); 
    }

    // Методи пошуку з попередніх ЛР
    public ArrayList<StoreItem> searchBySize(Size sz) {
        ArrayList<StoreItem> res = new ArrayList<>();
        for (StoreItem i : inventory) if (i.getClothes().getSize() == sz) res.add(i);
        return res;
    }

    public ArrayList<StoreItem> searchByBrand(String b) {
        ArrayList<StoreItem> res = new ArrayList<>();
        for (StoreItem i : inventory) if (i.getClothes().getBrand().equalsIgnoreCase(b)) res.add(i);
        return res;
    }
}