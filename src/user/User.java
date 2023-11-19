package user;

import item.BoughtItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    final private String name;
    private ArrayList<History> history;
    private ArrayList<BoughtItem> orders;

    public User(String name) {
        this.name = name;
        this.history = new ArrayList<>();
        this.orders = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public ArrayList<BoughtItem> getOrders () { return orders; }

    public void setOrders (ArrayList<BoughtItem> orders) { this.orders = orders; }

    public String toString() {
        return this.name;
    }

    public static ArrayList<BoughtItem> generalize(List<BoughtItem> items) {
        int[] q = {0};
        items.forEach(x-> {
            items.stream().filter(x::equals).forEach(y-> q[0] += y.getQuantity());
            x.setQuantity(q[0]);
            q[0] = 0;
        });
        List<BoughtItem> temp2 = new ArrayList<>();
        items.forEach(x->{
            if (temp2.stream().noneMatch(x::equals)) {
                temp2.add(x);
            }
        });
        return new ArrayList<>(temp2);
    }
}
