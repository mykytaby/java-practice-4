package org.example;

import java.util.UUID;

public class Shirts extends Clothes {
    private boolean shortSleeves;

    // Конструктор для створення НОВОГО об'єкта (UUID згенерується автоматично)
    public Shirts(String type, String brand, Size size, double price, boolean shortSleeves) {
        super(type, brand, size, price);
        this.shortSleeves = shortSleeves;
    }

    // Конструктор для завантаження ІСНУЮЧОГО об'єкта з файлу (з передачею UUID)
    public Shirts(UUID uuid, String type, String brand, Size size, double price, boolean shortSleeves) {
        super(uuid, type, brand, size, price);
        this.shortSleeves = shortSleeves;
    }

    public boolean isShortSleeves() { 
        return shortSleeves; 
    }

    public void setShortSleeves(boolean shortSleeves) {
        this.shortSleeves = shortSleeves;
    }

    @Override
    public String toDataString() {
        // Формат: Клас;UUID;Тип;Бренд;Розмір;Ціна;ВласнеПоле
        return "Shirts;" + getUuid() + ";" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + shortSleeves;
    }
}