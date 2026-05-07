package org.example;

/**
 * Виняток для ситуацій, коли об'єкт не знайдено в колекції.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}