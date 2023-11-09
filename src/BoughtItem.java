public class BoughtItem {
    private Item item;
    private int quantity;

    public BoughtItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public double getPrice() {
        return quantity * this.item.getPrice();
    }

    public String toString() {
        return this.item.getName() + "\tx" + this.quantity + "\t" + getPrice();
    }
}
