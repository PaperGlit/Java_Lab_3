import java.util.Objects;

public class Item {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static boolean isInvalidType(String s) {
        return !(Objects.equals(s, "Vegetable") || Objects.equals(s, "Fruit")
                || Objects.equals(s, "Meat") || Objects.equals(s, "Fish")
                || Objects.equals(s, "Other"));
    }

    public boolean hasEnoughQuantity(int i) {
        return (this.quantity >= i);
    }

    public void buyItem(int quantity) {
        this.quantity -= quantity;
    }

    public String toString() {
        return this.name + " (" + this.price + ") - (" + this.quantity + " left)";
    }
}
