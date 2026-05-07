CREATE TABLE IF NOT EXISTS clothes_inventory (
    id SERIAL PRIMARY KEY,
    class_type VARCHAR(50) NOT NULL, -- Дискримінатор (Pants, Shirts, etc.)
    item_type VARCHAR(100),
    brand VARCHAR(100),
    item_size VARCHAR(10),
    price DOUBLE PRECISION,
    extra_boolean BOOLEAN -- Для hasBelt, shortSleeves, isWaterproof, isEvening
);