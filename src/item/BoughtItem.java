package item;

import java.util.ArrayList;

public class BoughtItem extends Item {
    public BoughtItem(String name, String type, int quantity, double price) {
        super(name, type, quantity, price);
    }

    @Override
    public String toString() {
        return getName() + "\t" + getPrice() + "x" + getQuantity() + "(" + (getPrice() * getQuantity()) + ")";
    }

    public static boolean hasMeatOrFish(ArrayList<BoughtItem> items) {
        return items.stream().anyMatch(x -> x.getType().equals("Meat") || x.getType().equals("Fish"));
    }

    public static double total(ArrayList<BoughtItem> items) {
        return items.stream().mapToDouble(x->x.getPrice()*x.getQuantity()).sum();
    }

    public static String meatAndFish(ArrayList<BoughtItem> items) {
        String mAF = items.stream().filter(x -> x.getType().equals("Meat") || x.getType().equals("Fish")).map(Item::getName).reduce("", (ans, i) -> ans + i + ", ");
        return mAF.substring(0, mAF.length() - 2);
    }

    public static String print(ArrayList<BoughtItem> items) {
        StringBuilder sb = new StringBuilder();
        items.forEach(item -> sb.append(item.toString()).append("\n"));
        return sb.toString();
    }
}
