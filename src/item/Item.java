package item;

import java.io.Serializable;
import java.util.Objects;

public abstract class Item implements Serializable {
    private String name;
    private String type;
    private int quantity;
    private double price;

    public Item(String name, String type, int quantity, double price) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    void setN(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    void setT(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    void setP(double price) {
        this.price = price;
    }

    BoughtItem buyItem(int quantity) {
        this.quantity -= quantity;
        return new BoughtItem(this.name, this.type, quantity, this.price);
    }

    public boolean equals(Item item) {
        return (Objects.equals(getName(), item.getName()) && Objects.equals(getType(), item.getType()) && getPrice() == item.getPrice());
    }

    public static String pluralizer(String s) {
        if (s.endsWith("s") || s.endsWith("sh") || s.endsWith("ch") || s.endsWith("x") || s.endsWith("z")) {
            return s + "es";
        }
        else if (!(s.endsWith("ay") || s.endsWith("ey") || s.endsWith("iy") || s.endsWith("oy") || s.endsWith("uy"))) {
            return s.substring(0, s.length() - 2) + "ies";
        }
        else return s + "s";
    }

    public String pluralize() {
        if (this.quantity > 1) {
            return pluralizer(this.name);
        }
        else return this.name;
    }

    public abstract String toString();
}
