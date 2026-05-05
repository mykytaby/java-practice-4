package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Куртка", "Nike", Size.L, 1500.0);

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-100.0));
        assertThrows(IllegalArgumentException.class, () -> clothes.setBrand("   "));
        assertThrows(IllegalArgumentException.class, () -> clothes.setSize(null));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes("", "Adidas", Size.S, 500.0));
        assertThrows(IllegalArgumentException.class, () -> new Clothes("Штани", "Puma", Size.M, 0.0));
    }
}