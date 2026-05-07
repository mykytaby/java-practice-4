package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidPriceInSetter() {
        // Використовуємо Pants, бо Clothes тепер абстрактний клас
        Clothes pants = new Pants("Джинси", "Nike", Size.L, 1500.0, true);

        // Перевіряємо, що наша нова логіка кидає правильний виняток
        assertThrows(InvalidClothesDataException.class, () -> {
            pants.setPrice(-100.0);
        });
    }

    @Test
    void shouldCreateObjectSuccessfullyWithValidData() {
        // Перевіряємо створення об'єкта
        Clothes shirts = new Shirts("Поло", "Adidas", Size.S, 500.0, false);
        
        assertEquals(500.0, shirts.getPrice());
        assertEquals("Adidas", shirts.getBrand());
        assertEquals(Size.S, shirts.getSize());
    }
}