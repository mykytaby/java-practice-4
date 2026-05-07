package org.example;
import java.util.UUID;
public class Pants extends Clothes {
    private boolean hasBelt;
    public Pants(String type, String brand, Size size, double price, boolean hasBelt) { super(type, brand, size, price); this.hasBelt = hasBelt; }
    public Pants(UUID uuid, String type, String brand, Size size, double price, boolean hasBelt) { super(uuid, type, brand, size, price); this.hasBelt = hasBelt; }
    @Override
    public String toDataString() { return "Pants;" + getUuid() + ";" + getType() + ";" + getBrand() + ";" + getSize() + ";" + getPrice() + ";" + hasBelt; }
}