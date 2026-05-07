package org.example;

/**
 * Виняток для некоректних даних об'єкта (наприклад, від'ємна ціна).
 */
public class InvalidClothesDataException extends RuntimeException {
    public InvalidClothesDataException(String message) {
        super(message);
    }
}