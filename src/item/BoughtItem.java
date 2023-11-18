package item;

import fileService.FileService;
import user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class BoughtItem extends Item implements Serializable {
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

    public static <T> String print(ArrayList<T> items) {
        StringBuilder sb = new StringBuilder();
        items.forEach(item -> sb.append(item.toString()).append("\n"));
        return sb.toString();
    }

    public ArrayList<BoughtItem> order(User user, ArrayList<User> users, ArrayList<BoughtItem> cart, ArrayList<StoreItem> shelf) {
        StoreItem oi = shelf.stream().filter(x->x.equals(this)).toList().get(0);
        if (oi.getQuantity() < this.getQuantity()) {
            BoughtItem suborder = new BoughtItem(this.getName(), this.getType(), this.getQuantity() - oi.getQuantity(), this.getPrice());
            this.setQuantity(oi.getQuantity());
            cart.add(this);
            oi.setQuantity(0);
            shelf.removeIf(x->x.getQuantity() == 0);
            ArrayList<BoughtItem> orders = user.getOrders();
            orders.add(suborder);
            user.setOrders(orders);
            FileService.exportToFile(users, "users.ser");
            System.out.println("Your order of (" + this.getQuantity() + " / " + (this.getQuantity() + suborder.getQuantity()) + ") " + this.pluralize() + " was automatically added to your cart");
        }
        else {
            oi.setQuantity(oi.getQuantity() - this.getQuantity());
            shelf.removeIf(x->x.getQuantity() == 0);
            cart.add(this);
            System.out.println("Your order of " + this.getQuantity() + " " + this.pluralize() + " was successfully fulfilled and automatically added to your cart");
        }
        return cart;
    }
}
