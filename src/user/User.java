package user;

import item.BoughtItem;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    final private String name;
    private ArrayList<BoughtItem> history;
    private ArrayList<BoughtItem> orders;

    public User(String name) {
        this.name = name;
        this.history = new ArrayList<>();
        this.orders = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public ArrayList<BoughtItem> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<BoughtItem> history) {
        this.history = history;
    }

    public ArrayList<BoughtItem> getOrders () { return orders; }

    public void setOrders (ArrayList<BoughtItem> orders) { this.orders = orders; }

    public String toString() {
        return this.name;
    }
}
