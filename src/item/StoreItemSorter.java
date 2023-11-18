package item;

import java.util.Comparator;

public class StoreItemSorter implements Comparator<StoreItem> {
    @Override
    public int compare(StoreItem i1, StoreItem i2) {
        return Double.compare(i1.getPrice(), i2.getPrice());
    }
}