package item;

public abstract class Item {
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

    void setQ(int quantity) {
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

    public abstract String toString();
}
