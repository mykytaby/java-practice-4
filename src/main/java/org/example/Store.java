package org.example;
import java.util.ArrayList;

public class Store {
    private String name;
    private ArrayList<StoreItem> inventory;

    public Store(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
    }

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