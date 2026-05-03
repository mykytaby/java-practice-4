package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Куртка", "Nike", "L", 1500.0);

        // Перевіряємо, що встановлення від'ємної ціни кидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setPrice(-100.0);
        });
        
        // Перевіряємо, що встановлення порожнього рядка кидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setBrand("   ");
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        // Перевіряємо помилку при створенні з порожнім розміром
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Футболка", "Adidas", "", 500.0);
        });

        // Перевіряємо помилку при створенні з нульовою ціною
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Штани", "Puma", "M", 0.0);
        });
    }
}