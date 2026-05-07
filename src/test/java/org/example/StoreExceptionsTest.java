package org.example;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class StoreExceptionsTest {

    @Test
    void shouldThrowObjectNotFoundExceptionWhenSearchingNonExistingUUID() {
        Store store = new Store("Test Store");
        UUID fakeId = UUID.randomUUID();

        // Перевіряємо, що при пошуку неіснуючого UUID викидається наш виняток
        assertThrows(ObjectNotFoundException.class, () -> {
            store.findByUuid(fakeId);
        });
    }

    @Test
    void shouldThrowInvalidClothesDataExceptionWhenPriceIsNegative() {
        // Перевіряємо, що при спробі задати від'ємну ціну викидається наш виняток
        assertThrows(InvalidClothesDataException.class, () -> {
            // Використовуємо Pants, оскільки Clothes - абстрактний клас
            Pants pants = new Pants("Джинси", "TestBrand", Size.M, 1000, true);
            pants.setPrice(-50); // Це має викликати виняток
        });
    }
    
    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        Store store = new Store("Test Store");
        Pants pants = new Pants("Джинси", "Levis", Size.M, 1000, true);
        StoreItem fakeItem = new StoreItem(pants, 1);
        
        // Ми не додали item у store, тому видалення має кинути виняток
        assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(fakeItem);
        });
    }
}