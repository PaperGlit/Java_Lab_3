package item;

import java.io.Serializable;
import java.util.Objects;

public class StoreItem extends Item implements Serializable {
    public StoreItem(String name, String type, int quantity, double price) {
        super(name, type, quantity, price);
    }

    public void setName(String name) {
        setN(name);
    }

    public void setType(String type) {
        setT(type);
    }

    public void setPrice(double price) {
        setP(price);
    }

    public static boolean isInvalidType(String s) {
        return !(Objects.equals(s, "Vegetable") || Objects.equals(s, "Fruit")
                || Objects.equals(s, "Meat") || Objects.equals(s, "Fish")
                || Objects.equals(s, "Other"));
    }

    public boolean hasEnoughQuantity(int i) {
        return (getQuantity() >= i);
    }

    public BoughtItem buy(int quantity) {
        return buyItem(quantity);
    }

    @Override
    public String toString() {
        return getName() + " (" + getPrice() + ") - (" + getQuantity() + " left)";
    }
}
