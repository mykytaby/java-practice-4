package org.example;

import java.util.UUID;

public class Dress extends Clothes {
    private boolean isEvening;

    // Конструктор для створення НОВОГО об'єкта (UUID згенерується автоматично)
    public Dress(String type, String brand, Size size, double price, boolean isEvening) {
        super(type, brand, size, price);
        this.isEvening = isEvening;
    }

    // Конструктор для завантаження ІСНУЮЧОГО об'єкта з файлу (з передачею UUID)
    public Dress(UUID uuid, String type, String brand, Size size, double price, boolean isEvening) {
        super(uuid, type, brand, size, price);
        this.isEvening = isEvening;
    }

    public boolean isEvening() { 
        return isEvening; 
    }

    public void setEvening(boolean isEvening) {
        this.isEvening = isEvening;
    }

    @Override
    public String toDataString() {
        // Формат: Клас;UUID;Тип;Бренд;Розмір;Ціна;ВласнеПоле
        return "Dress;" + getUuid() + ";" + getType() + ";" + getBrand() + ";" + getS