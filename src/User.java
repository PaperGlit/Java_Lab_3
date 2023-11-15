import item.BoughtItem;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    final private String name;
    private ArrayList<BoughtItem> history;

    public User(String name) {
        this.name = name;
        this.history = new ArrayList<>();
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

    public String toString() {
        return this.name;
    }
}
