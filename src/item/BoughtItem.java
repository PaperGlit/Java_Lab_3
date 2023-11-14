package item;

public class BoughtItem extends Item {
    public BoughtItem(String name, String type, int quantity, double price) {
        super(name, type, quantity, price);
    }

    @Override
    public String toString() {
        return getName() + "\t" + getPrice() + "x" + getQuantity() + "(" + (getPrice() * getQuantity()) + ")";
    }
}
