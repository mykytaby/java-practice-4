package org.example;

import java.util.UUID;

public class Outerwear extends Clothes {
    private boolean isWaterproof;

    // Конструктор для створення НОВОГО об'єкта (UUID згенерується автоматично)
    public Outerwear(String type, String brand, Size size, double price, boolean isWaterproof) {
        super(type, brand, size, price);
        this.isWaterproof = isWaterproof;
    }

    // Конструктор для завантаження ІСНУЮЧОГО об'єкта з файлу (з передачею UUID)
    public Outerwear(UUID uuid, String type, String brand, Size size, double price, boolean isWaterproof) {
        super(uuid, type, brand, size, price);
        this.isWaterproof = isWaterproof;
    }

    public boolean isWaterproof() { 
        return isWaterproof; 
    }

    public void setWaterproof(boolean isWaterproof) {
        this.isWaterproof = isWaterproof;
    }

    @Override
    public String toDataString() {
        // Формат: Клас;UUID;Тип;Бренд;Розмір;Ціна;ВласнеПоле
        return "Outerwear;" + getUuid() + ";" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + isWaterproof;
    }
}